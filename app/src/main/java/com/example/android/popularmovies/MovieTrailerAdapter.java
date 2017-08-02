package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.data.MovieTrailer;
import com.example.android.popularmovies.utilities.ImdbUtils;

import java.net.URL;

import static com.example.android.popularmovies.R.drawable.video_play_icon;
import static com.example.android.popularmovies.R.id.movieTrailer;

/**
 * Created by hmaust on 7/23/17.
 */

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.MovieTrailerAdapterViewHolder> {
    private MovieTrailer[] mMovieTrailerData;
    private final MovieTrailerAdapterOnClickHandler mClickHandler;

    //The interface that receives onClick messages
    public interface MovieTrailerAdapterOnClickHandler {
        void onClick(String youtubeTrailerKey);
    }

    public MovieTrailerAdapter(MovieTrailerAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class MovieTrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mMovieTrailerTextView;
        final ImageView iconView;

        public MovieTrailerAdapterViewHolder(View view) {
            super(view);
            iconView = (ImageView) view.findViewById(R.id.play_icon);
            mMovieTrailerTextView = (TextView) view.findViewById(movieTrailer);

            view.setOnClickListener(this);

            Log.i(getClass().getName(), "movie trailer:" + mMovieTrailerData);
        }

        @Override
        public void onClick(View view) {
            Log.i(getClass().getName(), "MOVIE TRAILER: " + view.toString());
            String youTubeTrailerKey = "MKyp9Tx-NPY";
            mClickHandler.onClick(youTubeTrailerKey);
        }

    }

    @Override
    public MovieTrailerAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.trailer_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new MovieTrailerAdapter.MovieTrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieTrailerAdapterViewHolder holder, int position) {
        MovieTrailer movieTrailer = mMovieTrailerData[position];
        Log.i(getClass().getName(), "Displaying " + movieTrailer.getTrailerName());
        holder.iconView.setImageResource(R.drawable.video_play_icon);
        holder.mMovieTrailerTextView.setText(movieTrailer.getTrailerName());
    }

    @Override
    public int getItemCount() {
        if (mMovieTrailerData == null) {
            return 0;
        }
        return mMovieTrailerData.length;
    }

    public void setmMovieTrailerData(MovieTrailer[] movieTrailerData) {
        mMovieTrailerData = movieTrailerData;
        notifyDataSetChanged();

    }




}
