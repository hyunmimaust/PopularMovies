package com.example.android.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by hmaust on 7/31/17.
 */

public class MyFavoriteContract {
    /* Clients need to know how to access myFavorite data.
     these content URI's for the path to that data:
       1) Content authority,
       2) Base content URI,
       3) Path(s) to the tasks directory
       4) Content URI for data in the TaskEntry class
     */
    public static final String AUTHORITY = "com.example.android.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String TASKS = "tasks";

    //MyFavoriteEntry is an inner class that defines the contents of the myFavorite table
    public static final class MyFavoriteEntry implements BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TASKS).build();

        //TABLE_NAME -> myFavorite
        public static final String TABLE_NAME = "myFavorite";
        //COLUMN_MOVIE_NAME -> "movieName"
        public static final String COLUMN_MOVIE_ID = "movieId";
        //COLUMN_MOVIE_NAME -> "movieName"
        public static final String COLUMN_MOVIE_NAME = "movieName";
        //COLUMN_YEAR -> "year"
        public static final String COLUMN_YEAR = "year";
        //COLUMN_RATE -> "rate"
        public static final String COLUMN_RATE = "rate";
        //COLUMN_IMAGEURL -> "imageUrl"
        public static final String COLUMN_IMAGEURL="imageUrl";
        //COLUMN_DESCRIPTION -> "description"
        public static final String COLUMN_DESCRIPTION = "description";

    }
}
