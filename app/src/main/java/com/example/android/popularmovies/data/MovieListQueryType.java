package com.example.android.popularmovies.data;

/**
 * Created by hmaust on 7/2/17.
 */

public enum MovieListQueryType {
    MOSTPOPULAR_MOVIES("https://api.themoviedb.org/3/movie/popular?"),
    HIGHESTRATED_MOVIES("https://api.themoviedb.org/3/movie/top_rated?"),
    MYFAVORITE_MOVIES("https://api.themoviedb.org/3/movie/");

    String baseUrl;

    MovieListQueryType(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
