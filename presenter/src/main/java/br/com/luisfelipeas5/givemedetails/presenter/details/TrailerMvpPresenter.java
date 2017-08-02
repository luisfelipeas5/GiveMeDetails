package br.com.luisfelipeas5.givemedetails.presenter.details;

import br.com.luisfelipeas5.givemedetails.presenter.MvpPresenter;
import br.com.luisfelipeas5.givemedetails.view.details.TrailersMvpView;

public interface TrailerMvpPresenter extends MvpPresenter<TrailersMvpView>{
    void getTrailers(String movieId);
}
