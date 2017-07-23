package com.example.android.popularmovies.utilities;

import com.example.android.popularmovies.data.MovieReview;

import org.json.JSONException;

import java.net.URL;

/**
 * Created by hmaust on 7/22/17.
 */

public class FetchMovieReviewList {
    public static MovieReview[] fetch(String movieId) throws JSONException {
        String uriString = ImdbUtils.uriForMovieReviewList(movieId);
        URL url = ImdbUtils.buildUrl(uriString);
        String jsonString = ImdbUtils.getMoviesFromHttpUrl(url);
        return OpenPopularMoviesJsonUtils.getMovieReviewStringsFromJson(jsonString);

    }
}
