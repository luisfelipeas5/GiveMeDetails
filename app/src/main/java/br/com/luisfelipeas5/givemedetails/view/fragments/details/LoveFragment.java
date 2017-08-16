package br.com.luisfelipeas5.givemedetails.view.fragments.details;

import javax.inject.Inject;

import br.com.luisfelipeas5.givemedetails.presenter.details.LoveMvpPresenter;
import br.com.luisfelipeas5.givemedetails.view.details.LoveMvpView;

public class LoveFragment implements LoveMvpView {
    private LoveMvpPresenter presenter;
    private String mMovieId;

    @Inject
    public void setPresenter(LoveMvpPresenter presenter) {
        this.presenter = presenter;
        this.presenter.attach(this);
    }

    @Override
    public void setMovieId(String movieId) {
        mMovieId = movieId;
    }

    @Override
    public void onLoveClick() {
        presenter.loveMovieById(mMovieId);
    }

    @Override
    public void onMovieLoved(boolean withLove) {

    }

    @Override
    public void onLoveFailed() {

    }

    @Override
    public void onIsLovingMovie(boolean isLoving) {

    }
}
