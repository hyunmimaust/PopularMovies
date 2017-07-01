package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.data.Movie;
import com.squareup.picasso.Picasso;

import static com.example.android.popularmovies.PopularMovieAdapter.POPULARMOVIE_POSTER_BASE_URL;

/**
 * Created by hmaust on 6/30/17.
 */

public class DetailActivity extends AppCompatActivity {
    private ImageView mImageDisplay;
    private TextView mMovieInfoTitleDisplay;
    private TextView mMovieInfoYearDisplay;
    private TextView mMovieInfoRateDisplay;
    private TextView mMovieInfoOverviewDisplay;

    private String mMovieInfo;

    Movie movie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mMovieInfoTitleDisplay = (TextView) findViewById(R.id.tv_title_display);
        mMovieInfoYearDisplay = (TextView) findViewById(R.id.tv_movieInfo_year_display);
        //mMovieInfoRateDisplay = (TextView) findViewById(R.id.tv_movieInfo_rate_display);
        //mMovieInfoOverviewDisplay = (TextView) findViewById(R.id.tv_movieInfo_overview_display);
        //mMovieInfoYearDisplay = (TextView) findViewById(R.id.tv_movieInfo_year_display);

        Intent intentGetStartThisActivity = getIntent();
        movie = (Movie) intentGetStartThisActivity.getSerializableExtra("movie");

        if (intentGetStartThisActivity != null) {
            if (intentGetStartThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                mMovieInfo = intentGetStartThisActivity.getStringExtra(Intent.EXTRA_TEXT);
                mMovieInfoTitleDisplay.setText(movie.getTitle());
                mMovieInfoYearDisplay.setText("Relesed Date: " + movie.getRelease_date());
                //mMovieInfoRateDisplay.setText(movie.getPopularity().toString());
                //mMovieInfoOverviewDisplay.setText(movie.getOverview());
                //mMovieInfoDisplay.setText(mMovieInfo);
            }

            mImageDisplay = (ImageView) findViewById(R.id.iv_moviePosterSmall);
            Context context = mImageDisplay.getContext();
            Picasso.with(context).load(POPULARMOVIE_POSTER_BASE_URL + movie.getImageUrl()).into(mImageDisplay);
        }
    }


}
