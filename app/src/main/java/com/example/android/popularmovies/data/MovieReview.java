package com.example.android.popularmovies.data;

import java.io.Serializable;

import static android.R.attr.author;

/**
 * Created by hmaust on 7/22/17.
 */

public class MovieReview implements Serializable {
    String review_author;
    String review_content;
    String review_url;

    public String getAuthor() {
        return review_author;
    }

    public void setAuthor(String author) {
        this.review_author = author;
    }


    public String getContent() {
        return review_content;
    }

    public void setContent(String content) {
        this.review_content = content;
    }


    public String getUrl() {
        return review_url;
    }

    public void setUrl(String url) {
        this.review_url = url;
    }


    public String toString() {
        return "author: " + review_author + '\n' +
                "content: "+ review_content+'\n'+
                "url'" + review_url + '\n';
    }



}
