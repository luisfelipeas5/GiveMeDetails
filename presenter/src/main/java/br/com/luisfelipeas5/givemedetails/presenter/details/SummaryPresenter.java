package br.com.luisfelipeas5.givemedetails.presenter.details;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.SchedulerProvider;
import br.com.luisfelipeas5.givemedetails.view.details.SummaryMvpView;

public class SummaryPresenter implements SummaryMvpPresenter {
    public SummaryPresenter(MovieMvpDataManager movieMvpDataManager, SchedulerProvider schedulerProvider) {
    }

    @Override
    public void attach(SummaryMvpView summaryMvpView) {

    }

    @Override
    public void detachView() {

    }

    @Override
    public SchedulerProvider getSchedulerProvider() {
        return null;
    }
}
