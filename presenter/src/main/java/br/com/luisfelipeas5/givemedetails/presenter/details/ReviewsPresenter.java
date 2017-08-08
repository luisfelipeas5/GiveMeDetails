package br.com.luisfelipeas5.givemedetails.presenter.details;

import br.com.luisfelipeas5.givemedetails.presenter.BasePresenter;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.SchedulerProvider;
import br.com.luisfelipeas5.givemedetails.view.details.ReviewsMvpView;

class ReviewsPresenter extends BasePresenter<ReviewsMvpView> implements ReviewsMvpPresenter {

    ReviewsPresenter(SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
    }

    @Override
    public void attach(ReviewsMvpView reviewsMvpView) {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void getNextReviews(String id) {

    }

    @Override
    public int getCurrentPage() {
        return 0;
    }
}
