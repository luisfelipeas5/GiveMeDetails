package br.com.luisfelipeas5.givemedetails.presenter.details;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.presenter.BasePresenter;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.SchedulerProvider;
import br.com.luisfelipeas5.givemedetails.view.details.MoviePosterMvpView;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class MoviePosterPresenter extends BasePresenter<MoviePosterMvpView> implements MoviePosterMvpPresenter {
    private final MovieMvpDataManager mMovieMvpDataManager;
    private MoviePosterMvpView mMovieMvpView;

    public MoviePosterPresenter(MovieMvpDataManager movieMvpDataManager, SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
        mMovieMvpDataManager = movieMvpDataManager;
    }

    @Override
    public void attach(MoviePosterMvpView movieMvpView) {
        mMovieMvpView = movieMvpView;
    }

    @Override
    public void detachView() {
        mMovieMvpView = new MoviePosterMvpView() {

            @Override
            public void onMoviePosterUrlReady(String posterUrl) {

            }

            @Override
            public void onGetMoviePosterUrlFailed() {

            }

            @Override
            public void getPosterWidth() {

            }

            @Override
            public void onGetMovieTitleReady(String movieTitle) {

            }

            @Override
            public void onGetMovieTitleFailed() {

            }
        };
    }

    @Override
    public void getMoviePosterUrl(String movieId, int width) {
        SchedulerProvider schedulerProvider = getSchedulerProvider();
        mMovieMvpDataManager.getMoviePosterUrl(width, movieId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onSuccess(@NonNull String posterUrl) {
                        mMovieMvpView.onMoviePosterUrlReady(posterUrl);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mMovieMvpView.onGetMoviePosterUrlFailed();
                    }
                });
    }

    @Override
    public void getMovieTitle(String movieId) {
        SchedulerProvider schedulerProvider = getSchedulerProvider();
        mMovieMvpDataManager.getMovieTitle(movieId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onSuccess(@NonNull String movieTitle) {
                        mMovieMvpView.onGetMovieTitleReady(movieTitle);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mMovieMvpView.onGetMovieTitleFailed();
                    }
                });
    }
}
