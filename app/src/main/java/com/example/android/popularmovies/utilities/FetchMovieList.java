package com.example.android.popularmovies.utilities;

import android.content.Context;
import android.provider.ContactsContract;

import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.MovieListQueryType;
import com.example.android.popularmovies.data.MyFavoriteDAO;

import org.json.JSONException;

import java.net.URL;

/**
 * Created by hmaust on 7/22/17.
 */

public class FetchMovieList {
    public static Movie[] fetch(MovieListQueryType movieListQueryType) throws JSONException {
        String uriString = ImdbUtils.uriForSelectedJob(movieListQueryType);
        URL url = ImdbUtils.buildUrl(uriString);
        String jsonString = ImdbUtils.getMoviesFromHttpUrl(url);
        return OpenPopularMoviesJsonUtils.getPopularMoviesStringsFromJson(jsonString);
    }







}
