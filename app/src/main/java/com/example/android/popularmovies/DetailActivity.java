package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.MovieListQueryType;
import com.example.android.popularmovies.data.MovieReview;
import com.example.android.popularmovies.data.MovieTrailer;
import com.example.android.popularmovies.data.MyFavoriteDAO;
import com.example.android.popularmovies.utilities.FetchDataJson;
import com.example.android.popularmovies.utilities.ImdbUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.popularmovies.PopularMovieAdapter.POPULARMOVIE_POSTER_BASE_URL;

/**
 * Created by hmaust on 6/30/17.
 */

public class DetailActivity extends AppCompatActivity implements
        MovieTrailerAdapter.MovieTrailerAdapterOnClickHandler {

    MyFavoriteDAO mMyfavoriteDAO;
    private TextView mTrailerErrorMessageDisplay;
    private TextView mReviewErrorMessageDisplay;

    //Reference to RecyclerViews and Adapters for Movie Review List and Trailer List
    private MovieReviewAdapter mMovieReviewAdapter;
    private MovieTrailerAdapter mMovieTrailerAdapter;
    private RecyclerView mMovieReviewList;
    private RecyclerView mMovieTrailerList;
    private ProgressBar mMovieReviewLoadingIndicator;
    private ProgressBar mMovieTrailerLoadingIndicator;

    @BindView(R.id.iv_moviePosterSmall)
    ImageView mImageDisplay;

    @BindView(R.id.tv_title_display)
    TextView mMovieInfoTitleDisplay;

    @BindView(R.id.tv_movieInfo_year_display)
    TextView mMovieInfoYearDisplay;
    @BindView(R.id.tv_movieInfo_rate_display)
    TextView mMovieInfoVoteAverageDisplay;

    @BindView(R.id.tv_movieInfo_overview_display)
    TextView mMovieInfoOverviewDisplay;

    private MovieListQueryType movieListQueryType;
    private static final String LIFECYCLE_TRAILER_TEXT_KEY = "trailers";
    private static final String LIFECYCLE_REVIEW_TEXT_KEY = "reviews";
    private static final String TAG = DetailActivity.class.getSimpleName();

    Movie movie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        boolean trailerDataLoaded = false;
        boolean reviewDataLoaded = false;
        // This TextView is used to display errors and will be hidden if there are no errors
        mTrailerErrorMessageDisplay = (TextView) findViewById(R.id.tv_trailer_error_message_display);
        mReviewErrorMessageDisplay = (TextView) findViewById(R.id.tv_review_error_message_display);

        mMovieTrailerList = (RecyclerView) findViewById(R.id.rv_movieTrailer);
        mMovieReviewList = (RecyclerView) findViewById(R.id.rv_movieReview);

        //LinearLayoutManager displays movie Trailer data in the list
        LinearLayoutManager trailerLayoutManager = new LinearLayoutManager(this);
        //LinearLayoutManager displays movie Review data in the list
        LinearLayoutManager reviewLayoutManager = new LinearLayoutManager(this);

        mMovieTrailerList.setLayoutManager(trailerLayoutManager);
        mMovieReviewList.setLayoutManager(reviewLayoutManager);

        mMovieTrailerList.setHasFixedSize(true);
        mMovieReviewList.setHasFixedSize(true);

        mMovieTrailerAdapter = new MovieTrailerAdapter(this);
        mMovieReviewAdapter = new MovieReviewAdapter();

        mMovieTrailerList.setAdapter(mMovieTrailerAdapter);
        mMovieReviewList.setAdapter(mMovieReviewAdapter);

        mMovieTrailerLoadingIndicator = (ProgressBar) findViewById(R.id.pb_movieTrailer_loading_indicator);
        mMovieReviewLoadingIndicator = (ProgressBar) findViewById(R.id.pb_movieReview_loading_indicator);

        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            Log.d(TAG, "savedInstance in DetailActivity is not null ");

            if (savedInstanceState.containsKey(LIFECYCLE_TRAILER_TEXT_KEY)) {
                //restore movieDataList from mAdapter
                mMovieTrailerAdapter.setmMovieTrailerData((MovieTrailer[]) savedInstanceState.getSerializable(LIFECYCLE_TRAILER_TEXT_KEY));
                trailerDataLoaded = true;
                Log.d(TAG, "savedInstance is loaded from mMovieTrailerAdapter: " + trailerDataLoaded);
            }
            if (savedInstanceState.containsKey(LIFECYCLE_REVIEW_TEXT_KEY)) {
                //restore movieDataList from mAdapter
                mMovieReviewAdapter.setmMovieReviewData((MovieReview[]) savedInstanceState.getSerializable(LIFECYCLE_REVIEW_TEXT_KEY));
                reviewDataLoaded = true;
                Log.d(TAG, "savedInstance is loaded from mMovieReviewAdapter: " + reviewDataLoaded);
            }
        }

        Intent intentGetStartThisActivity = getIntent();
        movie = (Movie) intentGetStartThisActivity.getSerializableExtra("movie");
        movieListQueryType = (MovieListQueryType) intentGetStartThisActivity.getSerializableExtra("movieListQueryType");


        if (intentGetStartThisActivity != null) {
            if (intentGetStartThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                mMovieInfoTitleDisplay.setText(movie.getTitle());
                mMovieInfoYearDisplay.setText(movie.getRelease_year());
                mMovieInfoVoteAverageDisplay.setText(movie.getVoteAverage().toString() + "/10");
                mMovieInfoOverviewDisplay.setText(movie.getOverview());
            }

            Context context = mImageDisplay.getContext();
            Picasso.with(context).load(POPULARMOVIE_POSTER_BASE_URL + movie.getImageUrl()).into(mImageDisplay);

            String movieId = movie.getMovieId();

            if (!trailerDataLoaded)
                loadTrailerData(movieId);

            if (!reviewDataLoaded)
                loadReviewData(movieId);

            mMyfavoriteDAO = new MyFavoriteDAO(this);
        }
    }

    private void loadTrailerData(String movieId) {
        showTrailerDataView();

        new FetchMovieTrailerTask().execute(movieId);
    }

    private void loadReviewData(String movieId) {
        showReviewDataView();
        new FetchMovieReviewTask().execute(movieId);

    }

    private void showTrailerDataView() {
       /* the error is invisible */
        mTrailerErrorMessageDisplay.setVisibility(View.INVISIBLE);
        // Show mRecyclerView and the weather data is visible
        mMovieTrailerList.setVisibility(View.VISIBLE);

    }

    private void showReviewDataView() {
       /* the error is invisible */
        mReviewErrorMessageDisplay.setVisibility(View.INVISIBLE);
        // Show mRecyclerView and the weather data is visible
        mMovieReviewList.setVisibility(View.VISIBLE);
    }

    // Menu items for fetching trailers and reviews of the selected movie
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemThatwasSelected = item.getItemId();
        switch (menuItemThatwasSelected) {
            case android.R.id.home: {
                Intent mainActivityIntent = new Intent(this, MainActivity.class);
                mainActivityIntent.putExtra("movieListQueryType", movieListQueryType);
                Log.i(getClass().getName(), "Launching MainActivity with MovieListQueryType " + movieListQueryType);
                startActivity(mainActivityIntent);
                return true;
            }
            case R.id.highestRated_movie_list: {
                Intent mainActivityIntent = new Intent(this, MainActivity.class);
                mainActivityIntent.putExtra("movieListQueryType", MovieListQueryType.HIGHESTRATED_MOVIES);
                startActivity(mainActivityIntent);
                return true;
            }
            case R.id.mostPopular_movie_list: {
                Intent mainActivityIntent = new Intent(this, MainActivity.class);
                mainActivityIntent.putExtra("movieListQueryType", MovieListQueryType.MOSTPOPULAR_MOVIES);
                startActivity(mainActivityIntent);
                return true;
            }
            case R.id.myFavoritesCollection: {
                Intent mainActivityIntent = new Intent(this, MainActivity.class);
                mainActivityIntent.putExtra("movieListQueryType", MovieListQueryType.MYFAVORITE_MOVIES);
                startActivity(mainActivityIntent);
                return true;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMovieTrailerAdapter.getmMovieTrailerData() != null) {
            outState.putSerializable(LIFECYCLE_TRAILER_TEXT_KEY, mMovieTrailerAdapter.getmMovieTrailerData());
        }
        if (mMovieReviewAdapter.getmMovieReviewData() != null) {
            outState.putSerializable(LIFECYCLE_REVIEW_TEXT_KEY, mMovieReviewAdapter.getmMovieReviewData());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    class FetchMovieTrailerTask extends AsyncTask<String, Void, MovieTrailer[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mMovieTrailerLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected MovieTrailer[] doInBackground(String... params) {
            // There is no movie id, there's nothing to look up
            if (params.length == 0) {
                return null;
            }

            String movieId = params[0];

            try {
                return FetchDataJson.fetchTrailerList(movieId);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(MovieTrailer[] movieTrailer) {
            mMovieTrailerLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieTrailer != null) {
                showMovieTrailerDataView();
                mMovieTrailerAdapter.setmMovieTrailerData(movieTrailer);
            }

            Log.i(getClass().getName(), "LoadMovieTrailerString: " + movieTrailer.toString());
        }
    }

    class FetchMovieReviewTask extends AsyncTask<String, Void, MovieReview[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mMovieReviewLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected MovieReview[] doInBackground(String... params) {
            // There is no movie id, there's nothing to look up
            if (params.length == 0) {
                return null;
            }

            String movieId = params[0];

            try {
                return FetchDataJson.fetchReviewList(movieId);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(MovieReview[] movieReview) {
            //mMovieTrailerLoadingIndicator.setVisibility(View.INVISIBLE);
            mMovieReviewLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieReview != null) {
                showMovieReviewDataView();
                mMovieReviewAdapter.setmMovieReviewData(movieReview);
            }
        }
    }

    public void showMovieTrailerDataView() {
        mMovieTrailerList.setVisibility(View.VISIBLE);
    }

    public void showMovieReviewDataView() {
        mMovieReviewList.setVisibility(View.VISIBLE);
    }

    public void onClickMarkAsFavoriteButton(View view) {
        if (!getExistedMovieId()) {
            Uri addMyFavorite = mMyfavoriteDAO.addMyFavorite(movie);
            if (addMyFavorite !=null) {
                Log.i(getClass().getName(), "Toast Called: " + addMyFavorite);
                Toast.makeText(getApplicationContext(), "Marked As Favorite", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "It is not Marked As Favorite.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "It is already Marked As Favorite", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean getExistedMovieId() {
        String movieId = movie.getMovieId();
        return mMyfavoriteDAO.isFavorite(movieId);

    }


    @Override
    public void onClick(String trailerKey) {
        String trailerForYoutube = ImdbUtils.uriForYouTubeMovieTrailer(trailerKey);
        Uri webpage = Uri.parse(trailerForYoutube);

        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
