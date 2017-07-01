package com.example.android.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import com.example.android.popularmovies.Config;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * Created by hmaust on 6/22/17.
 */

public class ImdbUtils {
    private static final String TAG = ImdbUtils.class.getSimpleName();

    private static String apiKey = Config.apiKey;
    final static String POPULARMOVIES_BASE_URL = "https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc";

    private final static String API_KEY = "api_key";

    public static URL buildUrl() {
        Uri builtUri = Uri.parse(POPULARMOVIES_BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY, apiKey)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return url;
    }
    public static  String getMoviesFromHttpUrl(URL url) {
        try {
            String urlString = url.toString();
            System.out.println("UrlString " + urlString);

            URLConnection connection = url.openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\A");
            return scanner.next();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
