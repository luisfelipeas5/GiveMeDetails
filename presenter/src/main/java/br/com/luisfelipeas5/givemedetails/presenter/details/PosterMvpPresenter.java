package br.com.luisfelipeas5.givemedetails.presenter.details;

import br.com.luisfelipeas5.givemedetails.presenter.MvpPresenter;
import br.com.luisfelipeas5.givemedetails.view.details.MoviePosterMvpView;

public interface PosterMvpPresenter extends MvpPresenter<MoviePosterMvpView> {
    void getMoviePosterUrl(String movieId, int width);

    void getMovieTitle(String movieId);
}
