package br.com.luisfelipeas5.givemedetails.presenter.details;

import br.com.luisfelipeas5.givemedetails.presenter.MvpPresenter;
import br.com.luisfelipeas5.givemedetails.view.details.ReviewsMvpView;

interface ReviewsMvpPresenter extends MvpPresenter<ReviewsMvpView> {
    void getNextReviews(String id);

    int getCurrentPage();
}
