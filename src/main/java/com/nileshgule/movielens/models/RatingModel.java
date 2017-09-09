package com.nileshgule.movielens.models;

import java.io.Serializable;

public class RatingModel implements Serializable {
    public String getUserId() {
        return userId;
    }

    public String getMovieId() {
        return movieId;
    }

    public double getRating() {
        return rating;
    }

    private final String userId;
    private final String movieId;
    private final double rating;

    public RatingModel(String userId, String movieId, double rating) {

        this.userId = userId;
        this.movieId = movieId;
        this.rating = rating;
    }
}
