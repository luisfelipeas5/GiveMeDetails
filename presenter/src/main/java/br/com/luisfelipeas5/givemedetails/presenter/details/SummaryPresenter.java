package br.com.luisfelipeas5.givemedetails.presenter.details;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.model.Movie;
import br.com.luisfelipeas5.givemedetails.presenter.BasePresenter;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.SchedulerProvider;
import br.com.luisfelipeas5.givemedetails.view.details.SummaryMvpView;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class SummaryPresenter extends BasePresenter<SummaryMvpView> implements SummaryMvpPresenter {
    private final MovieMvpDataManager mMovieMvpDataManager;
    private SummaryMvpView mSummaryMvpView;

    public SummaryPresenter(MovieMvpDataManager movieMvpDataManager, SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
        mMovieMvpDataManager = movieMvpDataManager;
        detachView();
    }

    @Override
    public void attach(SummaryMvpView summaryMvpView) {
        mSummaryMvpView = summaryMvpView;
    }

    @Override
    public void detachView() {
        mSummaryMvpView = new SummaryMvpView() {
            @Override
            public void onSummaryFailed() {

            }

            @Override
            public void onMovieSummaryReady(Movie mMovie) {

            }
        };
    }

    @Override
    public void getSummary(String movieId) {
        SchedulerProvider schedulerProvider = getSchedulerProvider();
        mMovieMvpDataManager.getMovieSummary(movieId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new SingleObserver<Movie>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }
                    @Override
                    public void onSuccess(@NonNull Movie movie) {
                        mSummaryMvpView.onMovieSummaryReady(movie);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mSummaryMvpView.onSummaryFailed();
                    }
                });
    }
}
