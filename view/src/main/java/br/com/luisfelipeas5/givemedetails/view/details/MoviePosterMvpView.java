package br.com.luisfelipeas5.givemedetails.view.details;

import br.com.luisfelipeas5.givemedetails.view.MvpView;

public interface MoviePosterMvpView extends MvpView{
    void onMoviePosterUrlReady(String posterUrl);

    void onGetMoviePosterUrlFailed();

    void getPosterWidth();

    void onGetMovieTitleReady(String movieTitle);

    void onGetMovieTitleFailed();
}
