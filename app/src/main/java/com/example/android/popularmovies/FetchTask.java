package com.example.android.popularmovies;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.popularmovies.data.MovieReview;
import com.example.android.popularmovies.data.MovieTrailer;
import com.example.android.popularmovies.utilities.FetchDataJson;

import org.json.JSONException;

/**
 * Created by hmaust on 8/4/17.
 */

public class FetchTask extends AsyncTask<String, Void, MovieTrailer[]> {
    ProgressBar mLoadingIndicator;
    public FetchTask(ProgressBar loadingIndicator) {
        mLoadingIndicator = loadingIndicator;
    }

    @Override
    protected void onPreExecute () {
        super.onPreExecute();
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    protected MovieTrailer[] doInBackground (String...params){
        // There is no movie id, there's nothing to look up
        if (params.length == 0) {
            return null;
        }

        String movieId = params[0];

        try {
            return FetchDataJson.fetchTrailerList(movieId);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute (MovieTrailer[]movieTrailer){
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (movieTrailer != null) {
            //showMovieTrailerDataView();
            //mMovieTrailerAdapter.setmMovieTrailerData(movieTrailer);
        }

        Log.i(getClass().getName(), "LoadMovieTrailerString: " + movieTrailer.toString());
    }
}




