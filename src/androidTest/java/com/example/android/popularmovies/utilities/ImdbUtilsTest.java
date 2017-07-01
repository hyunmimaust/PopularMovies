package com.example.android.popularmovies.utilities;

import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

/**
 * Created by hmaust on 6/22/17.
 */
@RunWith(AndroidJUnit4.class)
public class ImdbUtilsTest {
    @Test
    public void getMovies() throws Exception {

        ImdbUtils imdbUtils = new ImdbUtils();
        Log.i(getClass().getName(), "Movies " + getMoviesFromHttpUrl.getMovies());

    }

}