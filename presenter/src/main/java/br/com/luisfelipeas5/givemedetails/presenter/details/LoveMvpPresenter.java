package br.com.luisfelipeas5.givemedetails.presenter.details;

import br.com.luisfelipeas5.givemedetails.presenter.MvpPresenter;
import br.com.luisfelipeas5.givemedetails.view.details.LoveMvpView;

public interface LoveMvpPresenter extends MvpPresenter<LoveMvpView> {
    void loveMovieById(String movieId);
}
