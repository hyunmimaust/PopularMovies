package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.data.MovieReview;

/**
 * Created by hmaust on 7/23/17.
 */

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.MovieReviewAdapterViewHolder> {

    private MovieReview[] mMovieReviewData;

    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
    }

    public MovieReviewAdapter() {

    }

    public class MovieReviewAdapterViewHolder extends RecyclerView.ViewHolder {
        public final TextView mMovieReviewTextView;

        public MovieReviewAdapterViewHolder(View view) {
            super(view);
            mMovieReviewTextView = (TextView) view.findViewById(R.id.tv_movieReview_data);
            Log.i(getClass().getName(), "movie review view:" + mMovieReviewData);
        }
    }

    @Override
    public MovieReviewAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.review_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new MovieReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieReviewAdapterViewHolder holder, int position) {
        MovieReview movieReview = mMovieReviewData[position];
        //Log.i(getClass().getName(), "Displaying " + movieReview.toString());
        holder.mMovieReviewTextView.setText(movieReview.toString());

    }

    @Override
    public int getItemCount() {
        if (mMovieReviewData == null) {
            return 0;
        }
        return mMovieReviewData.length;
    }

    public void setmMovieReviewData(MovieReview[] movieReviewData) {
        mMovieReviewData = movieReviewData;
        notifyDataSetChanged();

    }

    public MovieReview[] getmMovieReviewData() {
        return mMovieReviewData;
    }

}
