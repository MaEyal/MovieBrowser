package com.example.moviebrowser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    //Base JSON request url string with no page number
    private String JSON = "https://api.themoviedb.org/3/movie" +
        "/top_rated?api_key=811fc30cedd32aebab5591b946e86068&language=en-US&page=";

    private ArrayList<Movie> movies = new ArrayList<Movie>();
    private RecyclerView movieRecView;
    private int currentPage, pages;
    private FloatingActionButton back, next;
    MovieCellAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentPage = 1;
        movieRecView = findViewById(R.id.movRecView);
        back =  findViewById(R.id.back);
        next = findViewById(R.id.next);
        back.setEnabled(false);

        //Requesting JSON object and parsing it into the adapter
        (new GetData()).execute();

        //Next button: Requesting JSON for next page, initiating loading animation and disabling buttons
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage++;
                movies.clear();
                movieRecView.setVisibility(View.INVISIBLE);
                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                (new GetData()).execute();
                next.setClickable(false);
                back.setClickable(false);

            }
        });

        //Back button: Requesting JSON for previous page, initiating loading animation and disabling buttons
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage--;
                movies.clear();
                movieRecView.setVisibility(View.INVISIBLE);
                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                (new GetData()).execute();
                next.setClickable(false);
                back.setClickable(false);

            }
        });

    }


    //Requesting JSON object and parsing it into the adapter
    public class GetData extends AsyncTask<String,String,String>{

        public GetData() {
        }

        @Override
        protected String doInBackground(String... strings) {

            String current = "";

            try{
                URL url;
                HttpURLConnection urlConnection = null;

                try{
                    url = new URL("" + JSON + currentPage);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = urlConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                    int data = inputStreamReader.read();

                    //Reading the JSON object char by char creating a string object to parse.
                    while (data != -1){
                        current += (char)data;
                        data = inputStreamReader.read();
                    }
                    return current;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally{
                    if(urlConnection != null){
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s){
            try{
                JSONObject jsonObject = new JSONObject(s);
                pages = (int)jsonObject.get("total_pages");
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                //Parsing the JSON Array into the movies ArrayList to pass onto the adapter
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    movies.add( new Movie(
                            jsonObject1.getString("title"),
                            jsonObject1.getString("original_language"),
                            jsonObject1.getString("overview"),
                            "https://image.tmdb.org/t/p/w500" + jsonObject1.getString("poster_path"),
                            jsonObject1.getString("release_date"),
                            jsonObject1.getString("vote_average")

                    ));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            AddToRecycler(movies);
        }


    }

    private void AddToRecycler(ArrayList<Movie> movies) {
        adapter = new MovieCellAdapter(this);
        adapter.setMovies(movies);

        movieRecView.setAdapter(adapter);
        movieRecView.setLayoutManager(new GridLayoutManager(this, 2));
        //Disabling loading animation and enabling buttons (back and next, depending on the current page)
        findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
        movieRecView.setVisibility(View.VISIBLE);
        if (currentPage > 1){
            back.setEnabled(true);
            back.setClickable(true);
        }else{
            back.setEnabled(false);
        }
        if (currentPage < pages){
            next.setEnabled(true);
            next.setClickable(true);
        }else{
            next.setEnabled(false);
        }
    }



}




