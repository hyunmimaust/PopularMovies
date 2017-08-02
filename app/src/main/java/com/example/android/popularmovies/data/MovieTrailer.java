package com.example.android.popularmovies.data;

import java.io.Serializable;

/**
 * Created by hmaust on 7/22/17.
 */

public class MovieTrailer implements Serializable {

    String trailerId;
    String trailerKey;
    String trailerName;
    String trailerSite;
    String trailerSize;
    String trailerType;

    public String getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(String trailerId) {
        this.trailerId = trailerId;
    }

    public String getTrailerKey() {
        return trailerKey;
    }

    public void setTrailerKey(String trailerKey) {
        this.trailerKey = trailerKey;
    }

    public String getTrailerName() {
        return trailerName;
    }

    public void setTrailerName(String trailerName) {
        this.trailerName = trailerName;
    }

    public String getTrailerSite() {
        return trailerSite;
    }

    public void setTrailerSite(String trailerSite) {
        this.trailerSite = trailerSite;
    }

    public String getTrailerSize() {
        return trailerSize;
    }

    public void setTrailerSize(String trailerSize) {
        this.trailerSize = trailerSize;
    }

    public String getTrailerType() {
        return trailerType;
    }

    public void setTrailerType(String trailerType) {
        this.trailerType = trailerType;
    }

    public String toString() {
        return "name: " + trailerName + '\n' +
                "site: " + trailerSite + '\n' +
                "key'" + trailerKey + '\n';
    }

}
