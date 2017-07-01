package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.PopularMoviesPreference;
import com.example.android.popularmovies.utilities.ImdbUtils;
import com.example.android.popularmovies.utilities.OpenPopularMoviesJsonUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements PopularMovieAdapter.PopularMovieAdapterOnClickHandler {
    private PopularMovieAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Using findViewById, we get a reference to our RecyclerView from xml. This allows us to
         * do things like set the adapter of the RecyclerView and toggle the visibility.
         */
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_popularMovies);

        /* This TextView is used to display errors and will be hidden if there are no errors */
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        /*
         * LinearLayoutManager can support HORIZONTAL or VERTICAL orientations. The reverse layout
         * parameter is useful mostly for HORIZONTAL layouts that should reverse for right to left
         * languages.
         */
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        // COMPLETED (42) Use setHasFixedSize(true) on mRecyclerView to designate that all items in the list will have the same size
        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mRecyclerView.setHasFixedSize(true);

        /*
         * The PopularMovieAdapter is responsible for linking our popularMovie data with the Views that
         * will end up displaying our popularMovie data.
         */
        mAdapter = new PopularMovieAdapter(this);

        // Use mRecyclerView.setAdapter and pass in mAdapter
        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mRecyclerView.setAdapter(mAdapter);

        /*
         * The ProgressBar that will indicate to the user that we are loading data. It will be
         * hidden when no data is loading.
         *
         * Please note: This so called "ProgressBar" isn't a bar by default. It is more of a
         * circle. We didn't make the rules (or the names of Views), we just follow them.
         */
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        /* Once all of our views are setup, we can load the weather data. */
        loadPopularMoviesData();

    }

    /**
     * This method will get the user's preferred location for weather, and then tell some
     * background method to get the weather data in the background.
     */
    private void loadPopularMoviesData() {
        showPopularMoviesDataView();

        String location = PopularMoviesPreference.getPreferredWeatherLocation(this);
        new FetchPopularMoviesTask().execute(location);
    }

    @Override
    public void onClick(Movie popularMovieData) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        //pass the movie data to the DetalActivity
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, popularMovieData.toString());
        intentToStartDetailActivity.putExtra("movie", popularMovieData);
        startActivity(intentToStartDetailActivity);

    }

    /**
     * This method will make the View for the weather data visible and
     * hide the error message.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showPopularMoviesDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        // COMPLETED (44) Show mRecyclerView, not mWeatherTextView
        /* Then, make sure the weather data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the weather
     * View.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showErrorMessage() {
        // COMPLETED (44) Hide mRecyclerView, not mWeatherTextView
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public class FetchPopularMoviesTask extends AsyncTask<String, Void, Movie[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movie[] doInBackground(String... params) {

            /* If there's no zip code, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }

            //String location = params[0];
            URL popularMoviesRequestUrl = ImdbUtils.buildUrl();
            try {
                String jsonPopularMoviesResponse = ImdbUtils.getMoviesFromHttpUrl(popularMoviesRequestUrl);

                Movie[] jasonPopularMoviesData = OpenPopularMoviesJsonUtils
                        .getPopularMoviesStringsFromJson(MainActivity.this, jsonPopularMoviesResponse);

                return jasonPopularMoviesData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Movie[] popularMoviesData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (popularMoviesData != null) {
                showPopularMoviesDataView();
                // Instead of iterating through every string, use mAdapter.setWeatherData and pass in the weather data
                mAdapter.setPopularMovieData(popularMoviesData);
            } else {
                showErrorMessage();
            }
        }
    }

    // Use getMenuInflater()te to inflate the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // Return true to display this menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemThatwasSelected = item.getItemId();
        switch (menuItemThatwasSelected) {
            case R.id.action_refresh: {
                mAdapter.setPopularMovieData(null);
                loadPopularMoviesData();

                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
