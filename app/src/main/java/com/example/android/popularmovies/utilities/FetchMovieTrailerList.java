package com.example.android.popularmovies.utilities;

import com.example.android.popularmovies.data.MovieTrailer;

import org.json.JSONException;

import java.net.URL;

/**
 * Created by hmaust on 7/26/17.
 */

public class FetchMovieTrailerList {
    public static MovieTrailer[] fetch(String movieId) throws JSONException {
        String uriString = ImdbUtils.uriForMovieTrailerList(movieId);
        URL url = ImdbUtils.buildUrl(uriString);
        String jsonString = ImdbUtils.getMoviesFromHttpUrl(url);
        return OpenPopularMoviesJsonUtils.getMovieTrailerStringsFromJson(jsonString);
    }
}
