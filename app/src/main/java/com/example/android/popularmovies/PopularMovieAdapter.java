package com.example.android.popularmovies;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.data.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by hmaust on 6/22/17.
 */

public class PopularMovieAdapter extends RecyclerView.Adapter<PopularMovieAdapter.MovieViewHolder> {
    private Movie[] mMovieData;
    private final PopularMovieAdapterOnClickHandler mClickHandler;
    final static String POPULARMOVIE_POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185";

    public interface PopularMovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public PopularMovieAdapter(PopularMovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;

    }
    public class MovieViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mImageView;

        public MovieViewHolder(final View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.iv_moviePoster);

            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMovieClick(view, 0);
                }
            });

        }

        public void onMovieClick(View v, int column) {
            Movie selectedMovie = mMovieData[getAdapterPosition() + column];
            mClickHandler.onClick(selectedMovie);

        }
    }

    // inflate the list item xml into a view
    // return a new MovieViewHolder with the above view passed in as a parameter

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieViewHolder(view);

    }


    // Set the images of the ImageView to the popularMovie Posters for this list item's position

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        Movie movie = mMovieData[position];

        ImageView imageView = holder.mImageView;

        Context context = imageView.getContext();

        Picasso.with(context).load(POPULARMOVIE_POSTER_BASE_URL + movie.getImageUrl()).into(imageView);

    }


    // Return 0 if mMovieData is null, or the size of mMovieData if it is not null

    @Override
    public int getItemCount() {
        if (mMovieData == null) {
            return 0;
        }
        return mMovieData.length;
    }

    // saves the defaultMovieData to mMovieData
    public void setDefaultMovieData(Movie[] defaultMovieData) {
        mMovieData = defaultMovieData;
        notifyDataSetChanged();
    }



}
