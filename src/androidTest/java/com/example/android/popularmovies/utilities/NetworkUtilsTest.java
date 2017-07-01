package com.example.android.popularmovies.utilities;

import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.*;

/**
 * Created by hmaust on 6/22/17.
 */
public class NetworkUtilsTest {
    @Test
    public void buildUrl() throws Exception {
        URL url = NetworkUtils.buildUrl("Yorba Linda, CA");
        System.out.println(url);
    }

}