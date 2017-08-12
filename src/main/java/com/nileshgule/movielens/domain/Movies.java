package com.nileshgule.movielens.domain;

import java.util.List;

public class Movies {
    String movieId;
    String title;
    List<String> genres;

    public Movies(String movieId, String title, List<String> genres) {
        this.movieId = movieId;
        this.title = title;
        this.genres = genres;
    }
}
