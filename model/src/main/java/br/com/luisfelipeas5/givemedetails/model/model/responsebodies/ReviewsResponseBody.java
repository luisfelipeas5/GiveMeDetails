package br.com.luisfelipeas5.givemedetails.model.model.responsebodies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.model.reviews.ReviewTMDb;

public class ReviewsResponseBody {

    @SerializedName("results")
    private List<ReviewTMDb> reviews;

    public List<ReviewTMDb> getReviews() {
        return reviews;
    }
}
