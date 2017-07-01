package com.example.android.popularmovies.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.android.popularmovies.data.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.Date;

/**
 * Created by hmaust on 6/28/17.
 */

public final class OpenPopularMoviesJsonUtils {
    /**
     * This method parses JSON from a web response and returns an array of Strings
     * describing the popularMovies from the popularMovies API.
     * <p/>
     * Later on, we'll be parsing the JSON into structured data within the
     * getFullWeatherDataFromJson function, leveraging the data we have stored in the JSON. For
     * now, we just convert the JSON into human-readable strings.
     *
     * @param popularMoviesJsonStr JSON response from server
     *
     * @return Array of Strings describing weather data
     *
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static Movie[] getPopularMoviesStringsFromJson(Context context, String popularMoviesJsonStr)
            throws JSONException {

        /* Popular Movie information. Each movie's info is an element of the "list" array */
        final String MOVIES_RESULTS = "results";

        /* All MOVIE are children of the "MOVIE[]" object */
        final String MOVIE_TITLE = "title";

        final String MOVIE_POPULARITY = "popularity";
        final String MOVIE_POSTER_PATH = "poster_path";

        final String MOVIE_OVERVUEW = "overview";
        final String MOVIE_ORIGINAL_LANGUAGE = "original_language";

        final String MOVIE_ADULT = "adult";

        final String MOVIE_RELEASE_DATE = "release_date";
        final String MOVIE_MESSAGE_CODE = "cod";

        /* String array to hold each movie's info */
        Movie[] parsedMovieData = null;

        JSONObject popularMoviesJson = new JSONObject(popularMoviesJsonStr);

        /* Is there an error? */
        if (popularMoviesJson.has(MOVIE_MESSAGE_CODE)) {
            int errorCode = popularMoviesJson.getInt(MOVIE_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* HTTP invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }

        Log.d(OpenPopularMoviesJsonUtils.class.getName(), "JSON: " + popularMoviesJson.toString() );
        JSONArray movieArray = popularMoviesJson.getJSONArray(MOVIES_RESULTS);

        //Update size inside [] to proper size
        parsedMovieData = new Movie[movieArray.length()];

        for (int i = 0; i < movieArray.length(); i++) {

            /* These are the values that will be collected */
            String title;
            double popularity;
            String poster_path;
            String overview;
            String original_language;
            Boolean adult;
            String release_date;

            /* Get the JSON object representing the movie */
            JSONObject movieObject = movieArray.getJSONObject(i);

            Log.d(OpenPopularMoviesJsonUtils.class.getName(), "Movie JSON: " + movieObject.toString());

            Movie movie = new Movie();

            // TODO Populate movie object

            movie.setTitle(movieObject.getString("title"));

            movie.setPopularity(movieObject.getDouble("popularity"));
            movie.setImageUrl(movieObject.getString("poster_path"));
            movie.setOverview(movieObject.getString("overview"));
            movie.setOriginal_language(movieObject.getString("original_language"));
            movie.setAdult(movieObject.getBoolean("adult"));
            movie.setRelease_date(movieObject.getString("release_date"));


            Log.i(OpenPopularMoviesJsonUtils.class.getName(), "Movie: " + movie.toString());
            parsedMovieData[i] = movie;

        }

        return parsedMovieData;
    }

    /**
     * Parse the JSON and convert it into ContentValues that can be inserted into our database.
     *
     * @param context         An application context, such as a service or activity context.
     * @param forecastJsonStr The JSON to parse into ContentValues.
     *
     * @return An array of ContentValues parsed from the JSON.
     */
    public static ContentValues[] getFullWeatherDataFromJson(Context context, String forecastJsonStr) {
        /** This will be implemented in a future lesson **/
        return null;
    }
}
