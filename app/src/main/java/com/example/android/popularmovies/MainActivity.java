package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.QueryType;
import com.example.android.popularmovies.utilities.ImdbUtils;
import com.example.android.popularmovies.utilities.OpenPopularMoviesJsonUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements PopularMovieAdapter.PopularMovieAdapterOnClickHandler {
    private PopularMovieAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    QueryType queryType = QueryType.DEFAULT_MOVIES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Using findViewById, we get a reference to our RecyclerView from xml. This allows us to
        // do things like set the adapter of the RecyclerView and toggle the visibility.
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_popularMovies);

        // This TextView is used to display errors and will be hidden if there are no errors
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        // LinearLayoutManager can support VERTICAL orientations.
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        // setHasFixedSize(true) on mRecyclerView to designate that all items in the list will have the same size
        mRecyclerView.setHasFixedSize(true);

        /*
         * The PopularMovieAdapter is responsible for linking our popularMovie data with the Views that
         * will end up displaying our popularMovie data.
         */
        mAdapter = new PopularMovieAdapter(this);

        // Use mRecyclerView.setAdapter and pass in mAdapter
        // Setting the adapter attaches it to the RecyclerView in our layout.
        mRecyclerView.setAdapter(mAdapter);

        /*
         * The ProgressBar that will indicate to the user that we are loading data. It will be
         * hidden when no data is loading.
         */
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        // Once all of our views are setup, we can load the movie data.

        //loadDefaultMoviesData();

        Intent mainIntent = getIntent();

        if(mainIntent.hasExtra("queryType")){
            queryType = (QueryType) mainIntent.getSerializableExtra("queryType");
            Log.i(getClass().getName(), "Loading QueryType: " + queryType);
        }
        loadMovieData(queryType);
    }

    private void loadDefaultMoviesData() {
        showMoviesDataView();
        QueryType selectedJob = QueryType.DEFAULT_MOVIES;
        Log.d(getClass().getName(),"loadDefaultMovesData()");
        new FetchMovieDataTask().execute(selectedJob);
    }

    private void loadHighestRatedMoviesData() {
        showMoviesDataView();
        QueryType selectedJob = QueryType.HIGHESTRATED_MOVIES;
        Log.d(getClass().getName(),"loadHightestRatedMovesData()");
        new FetchMovieDataTask().execute(selectedJob);
    }

    private void loadMovieData(QueryType queryType){
        showMoviesDataView();
        new FetchMovieDataTask().execute(queryType);
    }

    @Override
    public void onClick(Movie popularMovieData) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        //pass the movie data to the DetalActivity
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, popularMovieData.toString());
        intentToStartDetailActivity.putExtra("movie", popularMovieData);
        intentToStartDetailActivity.putExtra("queryType", queryType);

        startActivity(intentToStartDetailActivity);

    }

    /*
     * This method will make the View for the mRecyclerView data visible and
     * hide the error message.
     */
    private void showMoviesDataView() {
        /* the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        // Show mRecyclerView and the weather data is visible
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        // Hide mRecyclerView
        // hide the currently visible data
        mRecyclerView.setVisibility(View.INVISIBLE);
        // show the error
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public class FetchMovieDataTask extends AsyncTask<QueryType, Void, Movie[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movie[] doInBackground(QueryType... params) {
            /* If there's no jobs, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }

            QueryType selectedJob = params[0];
            URL moviesRequestUrl;
            moviesRequestUrl = ImdbUtils.buildUrl(selectedJob);
            try {
                String jsonMoviesResponse = ImdbUtils.getMoviesFromHttpUrl(moviesRequestUrl);
                Movie[] jsonMoviesData = OpenPopularMoviesJsonUtils
                        .getPopularMoviesStringsFromJson(MainActivity.this, jsonMoviesResponse);

                return jsonMoviesData;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(Movie[] popularMoviesData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (popularMoviesData != null) {
                showMoviesDataView();
                // Instead of iterating through every string, use mAdapter.setDefaultMovieData and pass in the movie data
                mAdapter.setDefaultMovieData(popularMoviesData);
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
            case R.id.highestRated_movie_list: {
                mAdapter.setDefaultMovieData(null);
                //loadHighestRatedMoviesData();

                Intent mainActivityIntent = new Intent(this, MainActivity.class);
                mainActivityIntent.putExtra("queryType", QueryType.HIGHESTRATED_MOVIES);
                startActivity(mainActivityIntent);

                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
