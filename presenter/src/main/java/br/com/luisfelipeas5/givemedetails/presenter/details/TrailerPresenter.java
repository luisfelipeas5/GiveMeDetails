package br.com.luisfelipeas5.givemedetails.presenter.details;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.model.trailer.Trailer;
import br.com.luisfelipeas5.givemedetails.presenter.BasePresenter;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.SchedulerProvider;
import br.com.luisfelipeas5.givemedetails.view.details.TrailerMvpView;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class TrailerPresenter extends BasePresenter<TrailerMvpView> implements TrailerMvpPresenter {
    private final MovieMvpDataManager mMovieMvpDataManager;
    private TrailerMvpView mTrailerMvpView;

    public TrailerPresenter(SchedulerProvider schedulerProvider, MovieMvpDataManager movieMvpDataManager) {
        super(schedulerProvider);
        mMovieMvpDataManager = movieMvpDataManager;
    }

    @Override
    public void attach(TrailerMvpView trailerMvpView) {
        mTrailerMvpView = trailerMvpView;
    }

    @Override
    public void getTrailers(String movieId) {
        SchedulerProvider schedulerProvider = getSchedulerProvider();
        mMovieMvpDataManager.getMovieTrailers(movieId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new SingleObserver<List<Trailer>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onSuccess(@NonNull List<Trailer> trailers) {
                        mTrailerMvpView.onTrailersReady(trailers);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mTrailerMvpView.onGetTrailersFailed();
                    }
                });
    }

    @Override
    public void detachView() {
        mTrailerMvpView = new TrailerMvpView() {
            @Override
            public void onTrailersReady(List<Trailer> trailers) {

            }

            @Override
            public void onGetTrailersFailed() {

            }
        };
    }
}
