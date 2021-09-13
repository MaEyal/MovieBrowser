package com.example.moviebrowser;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


/*
This class implemets an adapter for our specifications, inheriting from the RecyclerView adapter.
In it a class of a ViewHolder which takes the information from the movie object and creates a ViewHolder instance of the movie_cell.xml which
was designed to hold some information. Put into cardview put on the recyclerview.
 */
public class MovieCellAdapter extends RecyclerView.Adapter<MovieCellAdapter.ViewHolder> {

    private ArrayList<Movie> movies = new ArrayList<>();
    private Context context;



    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public MovieCellAdapter(Context m) {
        context = m;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_cell,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.mName.setText(movies.get(position).getmName());
        holder.mRating.setText("Rating: " + movies.get(position).getmRating());
        holder.parent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MoviePage.class);
                i.putExtra("movie", (Parcelable) movies.get(position));
                context.startActivity(i);


            }
        });

        Glide.with(context).asBitmap().load(movies.get(position).getmImg()).into(holder.mImg);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mName, mRating;
        private CardView parent;
        private ImageView mImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.cellLayout);
            mName = itemView.findViewById(R.id.movName);
            mRating = itemView.findViewById(R.id.movRating);
            mImg = itemView.findViewById(R.id.movImg);
        }
    }
}
