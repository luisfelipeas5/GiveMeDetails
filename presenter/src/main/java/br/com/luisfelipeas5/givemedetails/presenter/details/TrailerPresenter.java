package br.com.luisfelipeas5.givemedetails.presenter.details;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.model.trailer.Trailer;
import br.com.luisfelipeas5.givemedetails.presenter.BasePresenter;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.SchedulerProvider;
import br.com.luisfelipeas5.givemedetails.view.details.TrailersMvpView;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class TrailerPresenter extends BasePresenter<TrailersMvpView> implements TrailerMvpPresenter {
    private final MovieMvpDataManager mMovieMvpDataManager;
    private TrailersMvpView mTrailersMvpView;

    public TrailerPresenter(SchedulerProvider schedulerProvider, MovieMvpDataManager movieMvpDataManager) {
        super(schedulerProvider);
        mMovieMvpDataManager = movieMvpDataManager;
    }

    @Override
    public void attach(TrailersMvpView trailersMvpView) {
        mTrailersMvpView = trailersMvpView;
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
                        mTrailersMvpView.onTrailersReady(trailers);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mTrailersMvpView.onGetTrailersFailed();
                    }
                });
    }

    @Override
    public void detachView() {
        mTrailersMvpView = new TrailersMvpView() {
            @Override
            public void onTrailersReady(List<Trailer> trailers) {

            }

            @Override
            public void onGetTrailersFailed() {

            }

            @Override
            public void setMovieId(String movieId) {

            }
        };
    }
}
