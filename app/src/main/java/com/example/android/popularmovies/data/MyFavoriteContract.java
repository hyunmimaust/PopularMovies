package com.example.android.popularmovies.data;

import android.provider.BaseColumns;

/**
 * Created by hmaust on 7/31/17.
 */

public class MyFavoriteContract {
    //BaseColumns interface
    public static final class MyFavoriteEntry implements BaseColumns{
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
