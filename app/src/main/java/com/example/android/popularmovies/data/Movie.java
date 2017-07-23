package com.example.android.popularmovies.data;

import java.io.Serializable;
import java.util.Date;

import static android.R.attr.id;

/**
 * Created by hmaust on 6/28/17.
 */

public class Movie implements Serializable {

    String imageUrl;
    String title;
    String original_language;

    Boolean adult;
    String overview;
    String release_date;
    Double popularity;
    Double vote_average;
    String movieId;

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String id) {
        this.movieId = id;
    }


    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_year() {
        String release_date = getRelease_date();
        String year = null;
        if (release_date.length() > 5) {
            year = release_date.substring(0, 4);
        }
        return year;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "imageUrl='" + imageUrl + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
