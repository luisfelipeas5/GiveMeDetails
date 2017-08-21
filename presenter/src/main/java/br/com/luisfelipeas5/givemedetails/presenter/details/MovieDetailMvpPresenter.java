package br.com.luisfelipeas5.givemedetails.presenter.details;

import br.com.luisfelipeas5.givemedetails.presenter.MvpPresenter;
import br.com.luisfelipeas5.givemedetails.view.details.MovieMvpView;

public interface MovieDetailMvpPresenter extends MvpPresenter<MovieMvpView> {
    void getMovie(String movieId);
}
