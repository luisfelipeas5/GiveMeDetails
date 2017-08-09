package br.com.luisfelipeas5.givemedetails.presenter.details;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.model.reviews.Review;
import br.com.luisfelipeas5.givemedetails.presenter.BasePresenter;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.SchedulerProvider;
import br.com.luisfelipeas5.givemedetails.view.details.ReviewsMvpView;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class ReviewsPresenter extends BasePresenter<ReviewsMvpView> implements ReviewsMvpPresenter {

    private int mCurrentPage = 0;
    private MovieMvpDataManager mMovieMvpDataManager;
    private ReviewsMvpView mReviewsMvpView;

    private boolean isGetting;

    public ReviewsPresenter(SchedulerProvider schedulerProvider, MovieMvpDataManager movieMvpDataManager) {
        super(schedulerProvider);
        mMovieMvpDataManager = movieMvpDataManager;
    }

    @Override
    public void attach(ReviewsMvpView reviewsMvpView) {
        mReviewsMvpView = reviewsMvpView;
    }

    @Override
    public void getNextReviews(String id) {
        if (isGetting) {
            return;
        }
        SchedulerProvider schedulerProvider = getSchedulerProvider();
        mMovieMvpDataManager.getMovieReviewsByPage(id, getCurrentPage())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new SingleObserver<List<Review>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        onGettingReviews(true);
                    }

                    @Override
                    public void onSuccess(@NonNull List<Review> reviews) {
                        mCurrentPage++;
                        mReviewsMvpView.onReviewsReady(reviews);
                        onGettingReviews(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mReviewsMvpView.onGetReviewsFailed();
                        onGettingReviews(false);
                    }
                });
    }

    private void onGettingReviews(boolean isGetting) {
        mReviewsMvpView.onGettingReviews(isGetting);
        this.isGetting = isGetting;
    }

    @Override
    public int getCurrentPage() {
        return mCurrentPage;
    }

    @Override
    public void detachView() {
        mReviewsMvpView = new ReviewsMvpView() {
            @Override
            public void onReviewsReady(List<Review> reviews) {
            }
            @Override
            public void onGetReviewsFailed() {
            }
            @Override
            public void setMovieId(String movieId) {
            }
            @Override
            public void onGettingReviews(boolean isGetting) {
            }
        };
    }
}
