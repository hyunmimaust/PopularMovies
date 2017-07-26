package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
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

import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.MovieListQueryType;
import com.example.android.popularmovies.data.MovieReview;
import com.example.android.popularmovies.data.MovieTrailer;
import com.example.android.popularmovies.utilities.FetchMovieReviewList;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.popularmovies.PopularMovieAdapter.POPULARMOVIE_POSTER_BASE_URL;

/**
 * Created by hmaust on 6/30/17.
 */

public class DetailActivity extends AppCompatActivity {
    //Reference to RecyclerViews and Adapters for Movie Review List and Trailer List
    private MovieReviewAdapter mMovieReviewAdapter;
    private MovieTrailerAdapter mMovieTrailerAdapter;
    private RecyclerView mMovieReviewList;
    private RecyclerView mMovieTrailerList;
    private ProgressBar mMovieReviewLadingIndicator;
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
    MovieReview mMovieReview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mMovieReviewList = (RecyclerView) findViewById(R.id.rv_movieReview);
        //LinearLayoutManager displays movie review data in the list
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mMovieReviewList.setLayoutManager(layoutManager);
        mMovieReviewList.setHasFixedSize(true);
        mMovieReviewAdapter = new MovieReviewAdapter();
        mMovieReviewList.setAdapter(mMovieReviewAdapter);
        mMovieReviewLadingIndicator =(ProgressBar) findViewById(R.id.pb_movieReview_loading_indicator);

        ButterKnife.bind(this);

        Intent intentGetStartThisActivity = getIntent();
        movie = (Movie) intentGetStartThisActivity.getSerializableExtra("movie");
        movieListQueryType = (MovieListQueryType) intentGetStartThisActivity.getSerializableExtra("movieListQueryType");

        if (intentGetStartThisActivity != null) {
            if (intentGetStartThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                mMovieInfoTitleDisplay.setText(movie.getTitle());
                mMovieInfoYearDisplay.setText(movie.getRelease_year());
                mMovieInfoVoteAverageDisplay.setText(movie.getVote_average().toString() + "/10");
                mMovieInfoOverviewDisplay.setText(movie.getOverview());
            }

            Context context = mImageDisplay.getContext();
            Picasso.with(context).load(POPULARMOVIE_POSTER_BASE_URL + movie.getImageUrl()).into(mImageDisplay);

            String movieId = movie.getMovieId();

            new FetchMovieReviewTask().execute(movieId);

        }

    }

    // Menu items for fetching trailers and reviews of the selected movie
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(getClass().getName(), "Options Item Selected: " + item + " , " + item.getItemId() + " , " + android.R.id.home);
        int action = item.getItemId();
        switch (action) {
            case android.R.id.home: {
                Intent mainActivityIntent = new Intent(this, MainActivity.class);
                mainActivityIntent.putExtra("movieListQueryType", movieListQueryType);
                Log.i(getClass().getName(), "Launching MainActivity with MovieListQueryType " + movieListQueryType);
                startActivity(mainActivityIntent);
                return true;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    class FetchMovieReviewTask extends AsyncTask<String, Void, MovieReview[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mMovieReviewLadingIndicator.setVisibility(View.VISIBLE);
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
            }
            catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(MovieReview[] movieReview) {
            mMovieReviewLadingIndicator.setVisibility(View.INVISIBLE);
            if(movieReview !=null){
                showMovieReviewDataView();
                mMovieReviewAdapter.setmMovieReviewData(movieReview);
            }
            //MovieReview[] movieReview;
            Log.i(getClass().getName(), "LoadMovieReviewString: " + movieReview.toString());


        }
    }

    private void showMovieReviewDataView(){
        mMovieReviewList.setVisibility(View.VISIBLE);
        //Log.i(getClass().getName(), "")
    }
}
