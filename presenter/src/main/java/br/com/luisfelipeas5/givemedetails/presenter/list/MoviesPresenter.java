package br.com.luisfelipeas5.givemedetails.presenter.list;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import br.com.luisfelipeas5.givemedetails.presenter.BasePresenter;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.SchedulerProvider;
import br.com.luisfelipeas5.givemedetails.view.list.MoviesMvpView;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class MoviesPresenter extends BasePresenter<MoviesMvpView> implements MoviesMvpPresenter {

    private MovieMvpDataManager mMovieMvpDataManager;
    private MoviesMvpView mMoviesMvpView;

    public MoviesPresenter(MovieMvpDataManager movieMvpDataManager, SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
        mMovieMvpDataManager = movieMvpDataManager;
    }

    @Override
    public void attach(MoviesMvpView moviesMvpView) {
        mMoviesMvpView = moviesMvpView;
    }

    @Override
    public void detachView() {
        mMoviesMvpView = getEmptyView();
    }

    private MoviesMvpView getEmptyView() {
        return new MoviesMvpView() {
            @Override
            public void onMoviesReady(List<Movie> movies) {
            }
            @Override
            public void onGetMoviesFailed() {
            }
            @Override
            public void onGettingMovies(boolean isGetting) {
            }
            @Override
            public void onGetMovies() {
            }
        };
    }

    @Override
    public void getPopularMovies() {
        SchedulerProvider schedulerProvider = getSchedulerProvider();
        mMovieMvpDataManager.getPopularMovies()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(getMoviesObserver());
    }

    @Override
    public void getTopRatedMovies() {
        SchedulerProvider schedulerProvider = getSchedulerProvider();
        mMovieMvpDataManager.getTopRatedMovies()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
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
