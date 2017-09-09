package com.nileshgule.movielens.functions;

import com.nileshgule.movielens.models.RatingModel;
import com.nileshgule.movielens.models.RatingModelBuilder;
import org.apache.spark.api.java.function.Function;

public class CreateRatingFunction implements Function<String, RatingModel> {
    @Override
    public RatingModel call(String line) throws Exception {
        String[] ratingDetails = line.split(",");

        String userId = ratingDetails[0];
        String movieId = ratingDetails[1];
        double rating = Double.parseDouble(ratingDetails[2]);

        RatingModel ratingModel = new RatingModelBuilder()
                .setUserId(userId)
                .setMovieId(movieId)
                .setRating(rating)
                .createRatingModel();

        return ratingModel;
    }
}
