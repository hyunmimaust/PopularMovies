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
import com.example.android.popularmovies.utilities.FetchMovieReviewList;
import com.example.android.popularmovies.utilities.FetchMovieTrailerList;
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

    Movie movie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


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

            new FetchMovieReviewTask().execute(movieId);
            new FetchMovieTrailerTask().execute(movieId);
            mMyfavoriteDAO = new MyFavoriteDAO(this);
        }
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
                return FetchMovieTrailerList.fetch(movieId);
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
                return FetchMovieReviewList.fetch(movieId);
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

    private void showMovieTrailerDataView() {
        mMovieTrailerList.setVisibility(View.VISIBLE);
    }

    private void showMovieReviewDataView() {
        mMovieReviewList.setVisibility(View.VISIBLE);
    }

    public void onClickMarkAsFavoriteButton(View view) {
        if (!getExistedMovieId()) {
            mMyfavoriteDAO.addMyfavorite(movie);
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
