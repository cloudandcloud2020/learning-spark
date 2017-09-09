package com.nileshgule.movielens.models;

import java.io.Serializable;


public class MovieModel  implements Serializable{

    public String getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getGenres() {
        return genres;
    }

    private final String movieId;
    private final String title;
    private final String genres;

    public MovieModel(String movieId, String title, String genres) {

        this.movieId = movieId;
        this.title = title;
        this.genres = genres;
    }


}
