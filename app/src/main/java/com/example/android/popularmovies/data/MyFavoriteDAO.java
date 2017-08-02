package com.example.android.popularmovies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by hmaust on 8/2/17.
 */

public class MyFavoriteDAO {
    private SQLiteDatabase mDb;
    public MyFavoriteDAO(Context context) {
        MyFavoriteDbHelper dbHelper = new MyFavoriteDbHelper(context);
        mDb = dbHelper.getWritableDatabase();
    }

    public boolean isFavorite(String movieId){
        String query = "select count(*) from " + MyFavoriteContract.MyFavoriteEntry.TABLE_NAME +
                " WHERE " + MyFavoriteContract.MyFavoriteEntry.COLUMN_MOVIE_ID + " = ?";

        String[] args = new String[]{movieId};

        Cursor cursor = mDb.rawQuery(query, args);
        cursor.moveToFirst();
        Log.d(getClass().getName(), "Cursor.getInt: " + cursor.getInt(0));
        return cursor.getInt(0) > 0;
    }

    public long addMyfavorite(Movie movie) {
        ContentValues cv = new ContentValues();

        cv.put(MyFavoriteContract.MyFavoriteEntry.COLUMN_MOVIE_ID, movie.getMovieId());
        cv.put(MyFavoriteContract.MyFavoriteEntry.COLUMN_MOVIE_NAME, movie.getTitle());
        cv.put(MyFavoriteContract.MyFavoriteEntry.COLUMN_YEAR, movie.getRelease_year());
        cv.put(MyFavoriteContract.MyFavoriteEntry.COLUMN_RATE, movie.getVoteAverage());
        cv.put(MyFavoriteContract.MyFavoriteEntry.COLUMN_IMAGEURL, movie.getImageUrl());
        cv.put(MyFavoriteContract.MyFavoriteEntry.COLUMN_DESCRIPTION, movie.getOverview());
        return mDb.insert(MyFavoriteContract.MyFavoriteEntry.TABLE_NAME, null, cv);

    }

    public Cursor getAllMyFavorite() {
        return mDb.query(
                MyFavoriteContract.MyFavoriteEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                MyFavoriteContract.MyFavoriteEntry._ID);
    }

}
