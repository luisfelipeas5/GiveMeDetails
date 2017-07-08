package br.com.luisfelipeas5.givemedetails.presenter.details;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.model.Movie;
import br.com.luisfelipeas5.givemedetails.presenter.BasePresenter;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.SchedulerProvider;
import br.com.luisfelipeas5.givemedetails.view.details.MovieMvpView;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

class MovieDetailPresenter extends BasePresenter<MovieMvpView> implements MovieDetailMvpPresenter {
    private final MovieMvpDataManager mMovieMvpDataManager;
    private MovieMvpView mMovieMvpView;

    MovieDetailPresenter(SchedulerProvider schedulerProvider, MovieMvpDataManager movieMvpDataManager) {
        super(schedulerProvider);
        mMovieMvpDataManager = movieMvpDataManager;
    }

    @Override
    public void attach(MovieMvpView movieMvpView) {
        mMovieMvpView = movieMvpView;
    }

    @Override
    public void detachView() {
        mMovieMvpView = new MovieMvpView() {
            @Override
            public void onMovieReady(Movie movie) {

            }

            @Override
            public void onGetMovieFailed() {

            }
        };
    }

    @Override
    public void getMovie(String movieId) {
        SchedulerProvider schedulerProvider = getSchedulerProvider();

        mMovieMvpDataManager.getMovie(movieId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new SingleObserver<Movie>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Movie movie) {
                        mMovieMvpView.onMovieReady(movie);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mMovieMvpView.onGetMovieFailed();
                    }
                });
    }
}
