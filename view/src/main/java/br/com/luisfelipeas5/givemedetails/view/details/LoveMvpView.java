package br.com.luisfelipeas5.givemedetails.view.details;

import br.com.luisfelipeas5.givemedetails.view.MvpView;

public interface LoveMvpView extends MvpView {
    void setMovieId(String movieId);

    void onLoveClick();

    void onMovieLoved(boolean withLove);

    void onLoveFailed();

    void onIsLovingMovie(boolean isLoving);
}
