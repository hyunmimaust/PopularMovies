package com.example.android.popularmovies.utilities;

import android.util.Log;

import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.MovieReview;
import com.example.android.popularmovies.data.MovieTrailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

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
     * @return Array of Strings describing weather data
     * @throws JSONException If JSON data cannot be properly parsed
     */
    private static final String MESSAGE_CODE = "cod";
    private static final String RESULTS = "results";
    /* All MOVIE are children of the "MOVIE[]" object */
    private static final String MOVIE_TITLE = "title";
    private static final String MOVIE_POPULARITY = "popularity";
    private static final String MOVIE_VOTE_AVERAGE = "vote_average";
    private static final String MOVIE_POSTER_PATH = "poster_path";
    private static final String MOVIE_OVERVIEW = "overview";
    private static final String MOVIE_ORIGINAL_LANGUAGE = "original_language";
    private static final String MOVIE_ADULT = "adult";
    private static final String MOVIE_RELEASE_DATE = "release_date";
    private static final String MOVIE_ID = "id";

    /* All movieTrailer are children of the "MovieTrailer[]" object */
    private static final String TRAILER_KEY ="key";
    private static final String TRAILER_NAME ="name";
    private static final String TRAILER_SITE="site";
    /* All movieReview are children of the "MovieReview[]" object */
    private static final String REVIEW_AUTHOR ="author";
    private static final String REVIEW_CONTENT ="content";
    private static final String REVIEW_URL="url";

    public static Movie[] getPopularMoviesStringsFromJson(String popularMoviesJsonStr)
            throws JSONException {
        /* String array to hold each movie's info */
        Movie[] parsedMovieData = null;

        JSONObject popularMoviesJson = new JSONObject(popularMoviesJsonStr);

        /* Is there an error? */
        if (popularMoviesJson.has(MESSAGE_CODE)) {
            int errorCode = popularMoviesJson.getInt(MESSAGE_CODE);

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

        Log.d(OpenPopularMoviesJsonUtils.class.getName(), "JSON: " + popularMoviesJson.toString());
        JSONArray movieArray = popularMoviesJson.getJSONArray(RESULTS);

        //Update size inside [] to proper size
        parsedMovieData = new Movie[movieArray.length()];

        for (int i = 0; i < movieArray.length(); i++) {

            /* Get the JSON object representing the movie */
            JSONObject movieObject = movieArray.getJSONObject(i);

            Log.d(OpenPopularMoviesJsonUtils.class.getName(), "Movie JSON: " + movieObject.toString());

            Movie movie = new Movie();

            // Populate movie object
            movie.setTitle(movieObject.getString(MOVIE_TITLE));
            movie.setVoteAverage(movieObject.getDouble(MOVIE_VOTE_AVERAGE));
            movie.setPopularity(movieObject.getDouble(MOVIE_POPULARITY));
            movie.setImageUrl(movieObject.getString(MOVIE_POSTER_PATH));
            movie.setOverview(movieObject.getString(MOVIE_OVERVIEW));
            movie.setOriginalLanguage(movieObject.getString(MOVIE_ORIGINAL_LANGUAGE));
            movie.setAdult(movieObject.getBoolean(MOVIE_ADULT));
            movie.setReleaseDate(movieObject.getString(MOVIE_RELEASE_DATE));
            movie.setMovieId(movieObject.getString(MOVIE_ID));

            Log.i(OpenPopularMoviesJsonUtils.class.getName(), "Movie: " + movie.toString());
            parsedMovieData[i] = movie;

        }

        return parsedMovieData;
    }

    public static MovieTrailer[] getMovieTrailerStringsFromJson(String movieTrailerStr)
    throws JSONException{
        /* String array to hold each movieTrailer's info */
        MovieTrailer[] parsedMovieTrailerData = null;

        JSONObject movieTrailerJson = new JSONObject(movieTrailerStr);

        /* Is there an error? */
        if (movieTrailerJson.has(MESSAGE_CODE)) {
            int errorCode = movieTrailerJson.getInt(MESSAGE_CODE);

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
        Log.d(OpenPopularMoviesJsonUtils.class.getName(), "JSON: " + movieTrailerJson.toString());
        JSONArray movieTrailerArray = movieTrailerJson.getJSONArray(RESULTS);

        //Update size inside [] to proper size
        parsedMovieTrailerData = new MovieTrailer[movieTrailerArray.length()];

        for (int i = 0; i < movieTrailerArray.length(); i++) {

            /* Get the JSON object representing the movie */
            JSONObject movieTrailerObject = movieTrailerArray.getJSONObject(i);

            Log.d(OpenPopularMoviesJsonUtils.class.getName(), "MovieReview JSON: " + movieTrailerObject.toString());

            MovieTrailer movieTrailer = new MovieTrailer();

            // Populate movieReview object
            movieTrailer.setTrailerKey(movieTrailerObject.getString(TRAILER_KEY));
            movieTrailer.setTrailerName(movieTrailerObject.getString(TRAILER_NAME));
            movieTrailer.setTrailerSite(movieTrailerObject.getString(TRAILER_SITE));

            Log.i(OpenPopularMoviesJsonUtils.class.getName(), "MovieTrailer: " + movieTrailer.toString());

            parsedMovieTrailerData[i] = movieTrailer;

        }

        return parsedMovieTrailerData;

    }

    public static MovieReview[] getMovieReviewStringsFromJson(String movieReviewJsonStr)
            throws JSONException {
        /* String array to hold each movieReview's info */
        MovieReview[] parsedMovieReviewData = null;

        JSONObject movieReviewJson = new JSONObject(movieReviewJsonStr);

        /* Is there an error? */
        if (movieReviewJson.has(MESSAGE_CODE)) {
            int errorCode = movieReviewJson.getInt(MESSAGE_CODE);

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

        Log.d(OpenPopularMoviesJsonUtils.class.getName(), "JSON: " + movieReviewJson.toString());
        JSONArray movieReviewArray = movieReviewJson.getJSONArray(RESULTS);

        //Update size inside [] to proper size
        parsedMovieReviewData = new MovieReview[movieReviewArray.length()];

        for (int i = 0; i < movieReviewArray.length(); i++) {

            /* Get the JSON object representing the movie */
            JSONObject movieReviewObject = movieReviewArray.getJSONObject(i);

            Log.d(OpenPopularMoviesJsonUtils.class.getName(), "MovieReview JSON: " + movieReviewObject.toString());

            MovieReview movieReview = new MovieReview();

            // Populate movieReview object
            movieReview.setAuthor(movieReviewObject.getString(REVIEW_AUTHOR));
            movieReview.setContent(movieReviewObject.getString(REVIEW_CONTENT));
            movieReview.setUrl(movieReviewObject.getString(REVIEW_URL));

            Log.i(OpenPopularMoviesJsonUtils.class.getName(), "MovieReview: " + movieReview.toString());

            parsedMovieReviewData[i] = movieReview;

        }

        return parsedMovieReviewData;

    }

}

