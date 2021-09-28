package com.example.animation.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.animation.Interfaces.OnMovieListener;
import com.example.animation.Models.DialogData;
import com.example.animation.Models.Movie;
import com.example.animation.MoviesDialog;
import com.example.animation.R;
import com.example.animation.Utils.Base;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.objectbox.Box;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private List<Movie> movies;
    private int rowLayout;
    private Context context;
    public MoviesAdapter(List<Movie> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(rowLayout,parent,false);
        return new MovieViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        String image_url = Base.IMAGE_URL_BASE_PATH + movies.get(position).getPosterPath();
        MoviesDialog moviesDialog=new MoviesDialog();
        Glide.with(context).load(image_url).placeholder(R.drawable.progress_animation).into(holder.movieImage);
        holder.movieTitle.setText(movies.get(position).getTitle());
        holder.data.setText(movies.get(position).getReleaseDate());
        holder.movieDescription.setText(movies.get(position).getOverview());
        holder.rating.setText(movies.get(position).getVoteAverage().toString());
        holder.imageMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moviesDialog.newInstance(new DialogData(movies.get(position).getPosterPath(),movies.get(position).getTitle())).show(((AppCompatActivity) v.getContext()).getSupportFragmentManager(),
                        "movie-dialog");
            }
        });



    }



    @Override
    public int getItemCount() {
        return movies.size();
    }
    public static class MovieViewHolder extends RecyclerView.ViewHolder{
        TextView movieTitle;
        TextView data;
        ImageButton imageMore;
        TextView movieDescription;
        TextView rating;
        ImageView movieImage;

        public MovieViewHolder(@NonNull View v) {
            super(v);
            movieImage = v.findViewById(R.id.movie_image);
            movieTitle = v.findViewById(R.id.title);
            data = v.findViewById(R.id.date);
            movieDescription = v.findViewById(R.id.description);
            imageMore=v.findViewById(R.id.more);
            rating = v.findViewById(R.id.rating);
        }

    }

}
