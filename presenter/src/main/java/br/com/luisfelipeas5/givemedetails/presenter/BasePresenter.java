package br.com.luisfelipeas5.givemedetails.presenter;

import br.com.luisfelipeas5.givemedetails.presenter.schedulers.SchedulerProvider;
import br.com.luisfelipeas5.givemedetails.view.MvpView;

public abstract class BasePresenter<T extends MvpView> implements MvpPresenter<T>{

    private SchedulerProvider mSchedulerProvider;

    public BasePresenter(SchedulerProvider schedulerProvider) {
        mSchedulerProvider = schedulerProvider;
    }

    @Override
    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }
}
