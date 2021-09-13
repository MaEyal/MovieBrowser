package com.example.moviebrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/*
This class implements a page that shows further details of a selected movie.
Receiving a parcelable movie class object and retrieving its information for display
 */
public class MoviePage extends AppCompatActivity {

    Movie movie;
    TextView mName,mGenre,mDetails,mRating,mRelease,mLang;
    ImageView mImg;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_page);
        movie = getIntent().getParcelableExtra("movie");
        setTitle(movie.getmName());
        context = getApplicationContext();
        mName = findViewById(R.id.movieName);
        mGenre = findViewById(R.id.movieRelease);
        mDetails = findViewById(R.id.movieDetails);
        mRating = findViewById(R.id.movieRating);
        mLang = findViewById(R.id.movieLang);
        mRelease = findViewById(R.id.movieRelease);
        mImg = findViewById(R.id.movImg2);
        mName.setText(movie.getmName());
        mDetails.setText(movie.getmDetails());
        mRating.setText("Rating: " + movie.getmRating());
        mRelease.setText("Released: " + movie.getmRelease());
        mLang.setText("Language: " + movie.getmLang());

        Glide.with(context).asBitmap().load(movie.getmImg()).into(mImg);

        Log.v("page", movie.toString());

    }
}