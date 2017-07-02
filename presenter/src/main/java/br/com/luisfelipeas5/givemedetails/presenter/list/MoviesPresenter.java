package br.com.luisfelipeas5.givemedetails.presenter.list;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.model.Movie;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.SchedulerProvider;
import br.com.luisfelipeas5.givemedetails.view.list.MoviesMvpView;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class MoviesPresenter implements MoviesMvpPresenter {

    private MovieMvpDataManager mMovieMvpDataManager;
    private MoviesMvpView mMoviesMvpView;
    private SchedulerProvider mSchedulerProvider;

    public MoviesPresenter(MovieMvpDataManager movieMvpDataManager, SchedulerProvider schedulerProvider) {
        mMovieMvpDataManager = movieMvpDataManager;
        mSchedulerProvider = schedulerProvider;
    }

    @Override
    public void attach(MoviesMvpView moviesMvpView) {
        mMoviesMvpView = moviesMvpView;
    }

    @Override
    public void getPopularMovies() {
        mMovieMvpDataManager.getPopularMovies()
                .observeOn(mSchedulerProvider.io())
                .subscribeOn(mSchedulerProvider.ui())
                .subscribe(getMoviesObserver());
    }

    @Override
    public void getTopRatedMovies() {
        mMovieMvpDataManager.getTopRatedMovies()
                .subscribe(getMoviesObserver());
    }

    private SingleObserver<List<Movie>> getMoviesObserver() {
        return new SingleObserver<List<Movie>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mMoviesMvpView.onGettingMovies(true);
            }

            @Override
            public void onSuccess(@NonNull List<Movie> movies) {
                mMoviesMvpView.onMoviesReady(movies);
                mMoviesMvpView.onGettingMovies(false);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mMoviesMvpView.onGetMoviesFailed();
                mMoviesMvpView.onGettingMovies(false);
            }
        };
    }
}
