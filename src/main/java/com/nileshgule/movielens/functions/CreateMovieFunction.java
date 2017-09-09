package com.nileshgule.movielens.functions;


import com.nileshgule.movielens.models.MovieModel;
import com.nileshgule.movielens.models.MovieModelBuilder;
import org.apache.spark.api.java.function.Function;

public class CreateMovieFunction implements Function<String, MovieModel> {

    @Override
    public MovieModel call(String line) throws Exception {
        String[] movieDetails = line.split(",");

        String movieId = movieDetails[0];
        String title = movieDetails[1];
        String genres = movieDetails[2];

        return new MovieModelBuilder()
                .setMovieId(movieId)
                .setTitle(title)
                .setGenres(genres)
                .createMovieModel();

    }
}
