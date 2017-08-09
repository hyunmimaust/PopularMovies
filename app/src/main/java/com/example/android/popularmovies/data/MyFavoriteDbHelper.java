package com.example.android.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hmaust on 7/31/17.
 */

public class MyFavoriteDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "myFavorite.db";
    private static final int DATABASE_VERSION = 6;

    public MyFavoriteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MYFAVORITE_TABLE = "CREATE TABLE " +
                MyFavoriteContract.MyFavoriteEntry.TABLE_NAME + "(" +
                MyFavoriteContract.MyFavoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MyFavoriteContract.MyFavoriteEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL, " +
                MyFavoriteContract.MyFavoriteEntry.COLUMN_MOVIE_NAME + " TEXT NOT NULL, " +
                MyFavoriteContract.MyFavoriteEntry.COLUMN_YEAR + " TEXT, " +
                MyFavoriteContract.MyFavoriteEntry.COLUMN_RATE + " TEXT, " +
                MyFavoriteContract.MyFavoriteEntry.COLUMN_IMAGEURL + " TEXT, " +
                MyFavoriteContract.MyFavoriteEntry.COLUMN_DESCRIPTION + " TEXT" + ");";
        db.execSQL(SQL_CREATE_MYFAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MyFavoriteContract.MyFavoriteEntry.TABLE_NAME);
        onCreate(db);
    }
}
