package br.com.luisfelipeas5.givemedetails.presenter.details;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.model.reviews.Review;
import br.com.luisfelipeas5.givemedetails.presenter.BasePresenter;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.SchedulerProvider;
import br.com.luisfelipeas5.givemedetails.view.details.ReviewsMvpView;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class ReviewsPresenter extends BasePresenter<ReviewsMvpView> implements ReviewsMvpPresenter {

    private int mCurrentPage = 0;
    private MovieMvpDataManager mMovieMvpDataManager;
    private ReviewsMvpView mReviewsMvpView;

    private boolean isGetting;
    private boolean mShowSeeAllButton;

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
        getReviews(id, getCurrentPage(), null);
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
    public void getReviewsPreviews(String movieId) {
        getReviews(movieId, 0, new Function<List<Review>, List<Review>>() {
            @Override
            public List<Review> apply(@NonNull List<Review> reviews) throws Exception {
                mShowSeeAllButton = true;
                if (reviews.size() > 3) {
                    return reviews.subList(0, 3);
                }
                return reviews;
            }
        });
    }

    private void getReviews(String movieId, int page, Function<List<Review>, List<Review>> mapper) {
        if (isGetting) {
            return;
        }
        SchedulerProvider schedulerProvider = getSchedulerProvider();
        Single<List<Review>> listSingle = mMovieMvpDataManager.getMovieReviewsByPage(movieId, page);
        if (mapper != null) {
            listSingle = listSingle.map(mapper);
        }
        listSingle.subscribeOn(schedulerProvider.io())
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

                        if (mShowSeeAllButton) {
                            mReviewsMvpView.showSeeAllReviewsButton();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mReviewsMvpView.onGetReviewsFailed();
                        onGettingReviews(false);
                    }
                });
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
            public void setMovieId(String movieId, boolean showPreview) {
            }
            @Override
            public void onGettingReviews(boolean isGetting) {
            }

            @Override
            public void showSeeAllReviewsButton() {

            }
        };
    }
}
