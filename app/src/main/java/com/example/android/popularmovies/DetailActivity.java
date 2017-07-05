package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.QueryType;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.popularmovies.PopularMovieAdapter.POPULARMOVIE_POSTER_BASE_URL;

/**
 * Created by hmaust on 6/30/17.
 */

public class DetailActivity extends AppCompatActivity {

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

    private QueryType queryType;

    Movie movie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        Intent intentGetStartThisActivity = getIntent();
        movie = (Movie) intentGetStartThisActivity.getSerializableExtra("movie");
        queryType = (QueryType) intentGetStartThisActivity.getSerializableExtra("queryType");

        if (intentGetStartThisActivity != null) {
            if (intentGetStartThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                mMovieInfoTitleDisplay.setText(movie.getTitle());
                mMovieInfoYearDisplay.setText(movie.getRelease_year());
                mMovieInfoVoteAverageDisplay.setText(movie.getVote_average().toString()+"/10");
                mMovieInfoOverviewDisplay.setText(movie.getOverview());
            }

            Context context = mImageDisplay.getContext();
            Picasso.with(context).load(POPULARMOVIE_POSTER_BASE_URL + movie.getImageUrl()).into(mImageDisplay);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d( getClass().getName(), "Options Item Selected: " + item + " , " + item.getItemId());
        if(16908332 == item.getItemId()){
            Intent mainActivityIntent = new Intent(this, MainActivity.class);
            mainActivityIntent.putExtra("queryType", queryType);
            Log.i(getClass().getName(), "Launching MainActivity with QueryType " + queryType);
            startActivity(mainActivityIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}