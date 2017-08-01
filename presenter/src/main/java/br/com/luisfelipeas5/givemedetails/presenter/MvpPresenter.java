package br.com.luisfelipeas5.givemedetails.presenter;

import br.com.luisfelipeas5.givemedetails.presenter.schedulers.SchedulerProvider;
import br.com.luisfelipeas5.givemedetails.view.MvpView;

public interface MvpPresenter<T extends MvpView> {
    void attach(T t);

    void detachView();

    SchedulerProvider getSchedulerProvider();
}
