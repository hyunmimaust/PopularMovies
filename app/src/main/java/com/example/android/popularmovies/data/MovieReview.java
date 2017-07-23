package com.example.android.popularmovies.data;

import java.io.Serializable;

/**
 * Created by hmaust on 7/22/17.
 */

public class MovieReview implements Serializable {
    String author;
    String content;
    String url;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String toString() {
        return "MovieReview{" +
                "author='" + author + '\'' +
                ", url'" + url + '\'' +
                '}';
    }



}
