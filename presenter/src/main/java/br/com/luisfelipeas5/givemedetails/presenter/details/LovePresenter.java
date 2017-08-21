package br.com.luisfelipeas5.givemedetails.presenter.details;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.presenter.BasePresenter;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.SchedulerProvider;
import br.com.luisfelipeas5.givemedetails.view.details.LoveMvpView;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class LovePresenter extends BasePresenter<LoveMvpView> implements LoveMvpPresenter {
    private final MovieMvpDataManager mMovieMvpDataManager;
    private LoveMvpView mLoveMvpView;

    public LovePresenter(SchedulerProvider schedulerProvider, MovieMvpDataManager movieMvpDataManager) {
        super(schedulerProvider);
        mMovieMvpDataManager = movieMvpDataManager;
    }

    @Override
    public void attach(LoveMvpView loveMvpView) {
        mLoveMvpView = loveMvpView;
    }

    @Override
    public void detachView() {
        mLoveMvpView = null;
    }

    @Override
    public void loveMovieById(String movieId) {
        SchedulerProvider schedulerProvider = getSchedulerProvider();
        mMovieMvpDataManager.toggleMovieLove(movieId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mLoveMvpView.onIsLovingMovie(true);
                    }

                    @Override
                    public void onSuccess(@NonNull Boolean withLove) {
                        mLoveMvpView.onIsLovingMovie(false);
                        mLoveMvpView.onMovieLoved(withLove);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mLoveMvpView.onIsLovingMovie(false);
                        mLoveMvpView.onLoveFailed();
                    }
                });
    }

    @Override
    public void onMovieIdReady(String movieId) {
        SchedulerProvider schedulerProvider = getSchedulerProvider();
        mMovieMvpDataManager.isMovieLoved(movieId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mLoveMvpView.onIsLovingMovie(true);
                    }

                    @Override
                    public void onSuccess(@NonNull Boolean isLoved) {
                        mLoveMvpView.onIsLovingMovie(false);
                        mLoveMvpView.onMovieLoved(isLoved);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mLoveMvpView.onIsLovingMovie(false);
                        mLoveMvpView.onLoveFailed();
                    }
                });
    }
}
