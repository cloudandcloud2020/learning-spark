package com.nileshgule.movielens.models;

public class RatingModelBuilder {
    private String userId;
    private String movieId;
    private double rating;

    public RatingModelBuilder setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public RatingModelBuilder setMovieId(String movieId) {
        this.movieId = movieId;
        return this;
    }

    public RatingModelBuilder setRating(double rating) {
        this.rating = rating;
        return this;
    }

    public RatingModel createRatingModel() {
        return new RatingModel(userId, movieId, rating);
    }
}