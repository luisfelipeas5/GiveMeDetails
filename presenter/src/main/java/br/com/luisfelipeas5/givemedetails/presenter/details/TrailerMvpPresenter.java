package br.com.luisfelipeas5.givemedetails.presenter.details;

import br.com.luisfelipeas5.givemedetails.presenter.MvpPresenter;
import br.com.luisfelipeas5.givemedetails.view.details.TrailerMvpView;

public interface TrailerMvpPresenter extends MvpPresenter<TrailerMvpView>{
    void getTrailers(String movieId);
}
