package com.nileshgule.movielens.models;

public class MovieModelBuilder {
    private String movieId;
    private String title;
    private String genres;

    public MovieModelBuilder setMovieId(String movieId) {
        this.movieId = movieId;
        return this;
    }

    public MovieModelBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public MovieModelBuilder setGenres(String genres) {
        this.genres = genres;
        return this;
    }

    public MovieModel createMovieModel() {
        return new MovieModel(movieId, title, genres);
    }
}