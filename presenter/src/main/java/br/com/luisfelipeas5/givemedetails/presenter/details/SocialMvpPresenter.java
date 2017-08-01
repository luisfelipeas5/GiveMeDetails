package br.com.luisfelipeas5.givemedetails.presenter.details;

import br.com.luisfelipeas5.givemedetails.presenter.MvpPresenter;
import br.com.luisfelipeas5.givemedetails.view.details.SocialMvpView;

public interface SocialMvpPresenter extends MvpPresenter<SocialMvpView>{
    void getSocialById(String id);
}
