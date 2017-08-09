package com.example.android.popularmovies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.popularmovies.DetailActivity;

/**
 * Created by hmaust on 8/2/17.
 */

public class MyFavoriteDAO {

    private static final String TAG = MyFavoriteDAO.class.getSimpleName();

    Context context;

    public MyFavoriteDAO(Context context) {
        this.context = context;
    }

    public boolean isFavorite(String movieId) {

        String[] args = new String[]{movieId};
        try {
            Cursor cursor = context.getContentResolver().query(MyFavoriteContract.MyFavoriteEntry.CONTENT_URI,
                    null,
                    MyFavoriteContract.MyFavoriteEntry.COLUMN_MOVIE_ID + "=?",
                    args,
                    null);
            cursor.moveToFirst();
            Log.d(getClass().getName(), "Cursor.getInt: " + cursor.getInt(0));
            return cursor.getInt(0)>0;
        } catch (Exception e){
            return false;
        }

}

    public Cursor getAllMyFavorite() {
        try {
            return context.getContentResolver().query(MyFavoriteContract.MyFavoriteEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    MyFavoriteContract.MyFavoriteEntry._ID);
        } catch (Exception e) {
            Log.e(TAG, "Failed to get all my favorite collection.");
            return null;
        }
    }

    public Uri addMyFavorite(Movie movie) {
        ContentValues cv = new ContentValues();

        cv.put(MyFavoriteContract.MyFavoriteEntry.COLUMN_MOVIE_ID, movie.getMovieId());
        cv.put(MyFavoriteContract.MyFavoriteEntry.COLUMN_MOVIE_NAME, movie.getTitle());
        cv.put(MyFavoriteContract.MyFavoriteEntry.COLUMN_YEAR, movie.getRelease_year());
        cv.put(MyFavoriteContract.MyFavoriteEntry.COLUMN_RATE, movie.getVoteAverage());
        cv.put(MyFavoriteContract.MyFavoriteEntry.COLUMN_IMAGEURL, movie.getImageUrl());
        cv.put(MyFavoriteContract.MyFavoriteEntry.COLUMN_DESCRIPTION, movie.getOverview());
        Uri uri = context.getContentResolver().insert(MyFavoriteContract.MyFavoriteEntry.CONTENT_URI, cv);
        return uri;

    }

    public Movie[] getMovieObjectFromCursor(Cursor cursor, int numberOfMovies) {
            /* String array to hold myfavorite movie collection */
        Movie[] myFavoriteMovieData = new Movie[numberOfMovies];

        if (numberOfMovies == 0)
            return null;

        for (int i = 0; i < numberOfMovies; i++) {
            cursor.moveToNext();
            Movie myFavorite = new Movie();
            myFavorite.setMovieId(cursor.getString(cursor.getColumnIndex(MyFavoriteContract.MyFavoriteEntry.COLUMN_MOVIE_ID)));
            myFavorite.setTitle(cursor.getString(cursor.getColumnIndex(MyFavoriteContract.MyFavoriteEntry.COLUMN_MOVIE_NAME)));
            myFavorite.setReleaseDate(cursor.getString(cursor.getColumnIndex(MyFavoriteContract.MyFavoriteEntry.COLUMN_YEAR)));
            myFavorite.setVoteAverage(cursor.getDouble(cursor.getColumnIndex(MyFavoriteContract.MyFavoriteEntry.COLUMN_RATE)));
            myFavorite.setImageUrl(cursor.getString(cursor.getColumnIndex(MyFavoriteContract.MyFavoriteEntry.COLUMN_IMAGEURL)));
            myFavorite.setOverview(cursor.getString(cursor.getColumnIndex(MyFavoriteContract.MyFavoriteEntry.COLUMN_DESCRIPTION)));
            myFavoriteMovieData[i] = myFavorite;
        }
        return myFavoriteMovieData;
    }

}