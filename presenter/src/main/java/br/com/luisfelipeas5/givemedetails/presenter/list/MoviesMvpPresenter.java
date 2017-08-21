package br.com.luisfelipeas5.givemedetails.presenter.list;

import br.com.luisfelipeas5.givemedetails.presenter.MvpPresenter;
import br.com.luisfelipeas5.givemedetails.view.list.MoviesMvpView;

public interface MoviesMvpPresenter extends MvpPresenter<MoviesMvpView> {
    void getPopularMovies();

    void getTopRatedMovies();

    void getLovedMovies();
}
