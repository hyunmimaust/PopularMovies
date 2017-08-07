package com.example.android.popularmovies.utilities;

import android.util.Log;

import com.example.android.popularmovies.MainActivity;
import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.MovieListQueryType;
import com.example.android.popularmovies.data.MovieReview;
import com.example.android.popularmovies.data.MovieTrailer;

import org.json.JSONException;

import java.net.URL;

/**
 * Created by hmaust on 8/4/17.
 */

public class FetchDataJson {
    private static final String TAG = FetchDataJson.class.getSimpleName();

    //For movie list
    public static Movie[] fetchMovieList(MovieListQueryType movieListQueryType) throws JSONException {
        String uriString = ImdbUtils.uriForSelectedJob(movieListQueryType);
        URL url = ImdbUtils.buildUrl(uriString);
        String jsonString = ImdbUtils.getMoviesFromHttpUrl(url);
        Log.i(TAG, "Movie[] fetched: " + url.toString());
        return OpenPopularMoviesJsonUtils.getPopularMoviesStringsFromJson(jsonString);
    }

    //For Review list
    public static MovieReview[] fetchReviewList(String movieId) throws JSONException {
        String uriString = ImdbUtils.uriForMovieReviewList(movieId);
        URL url = ImdbUtils.buildUrl(uriString);
        String jsonString = ImdbUtils.getMoviesFromHttpUrl(url);
        Log.i(TAG, "MovieReview[] fetched: " + url.toString());
        return OpenPopularMoviesJsonUtils.getMovieReviewStringsFromJson(jsonString);
    }

    //For Trailer list
    public static MovieTrailer[] fetchTrailerList(String movieId) throws JSONException {
        String uriString = ImdbUtils.uriForMovieTrailerList(movieId);
        URL url = ImdbUtils.buildUrl(uriString);
        String jsonString = ImdbUtils.getMoviesFromHttpUrl(url);
        Log.i(TAG, "MovieTrailer[] fetched: " + url.toString());
        return OpenPopularMoviesJsonUtils.getMovieTrailerStringsFromJson(jsonString);
    }

}
