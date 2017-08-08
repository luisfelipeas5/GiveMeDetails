package br.com.luisfelipeas5.givemedetails.view.details;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.model.reviews.Review;
import br.com.luisfelipeas5.givemedetails.view.MvpView;

public interface ReviewsMvpView extends MvpView {
    void onReviewsReady(List<Review> reviews);

    void onGetReviewsFailed();

    void setMovieId(String movieId);
}
