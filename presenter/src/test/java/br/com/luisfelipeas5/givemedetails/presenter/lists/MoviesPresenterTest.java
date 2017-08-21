package br.com.luisfelipeas5.givemedetails.presenter.lists;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import br.com.luisfelipeas5.givemedetails.presenter.list.MoviesMvpPresenter;
import br.com.luisfelipeas5.givemedetails.presenter.list.MoviesPresenter;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.SchedulerProvider;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.TestSchedulerProvider;
import br.com.luisfelipeas5.givemedetails.view.list.MoviesMvpView;
import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MoviesPresenterTest {

    private MoviesMvpPresenter mMoviesMvpPresenter;
    private TestScheduler mTestScheduler;
    @Mock
    private MovieMvpDataManager mMovieMvpDataManager;
    @Mock
    private MoviesMvpView mMoviesMvpView;
    @Mock
    private List<Movie> mPopularMoviesMocked;
    @Mock
    private List<Movie> mTopRatedMoviesMocked;
    @Mock
    private List<Movie> mLovedMoviesMocked;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(mMovieMvpDataManager.getLovedMovies()).thenReturn(Single.just(mLovedMoviesMocked));

        mTestScheduler = new TestScheduler();
        SchedulerProvider schedulerProvider = new TestSchedulerProvider(mTestScheduler);
        mMoviesMvpPresenter = new MoviesPresenter(mMovieMvpDataManager, schedulerProvider);
        mMoviesMvpPresenter.attach(mMoviesMvpView);
    }

    @Test
    public void deliveryToView_whenGetPopularMovies_success() {
        when(mMovieMvpDataManager.getPopularMovies()).thenReturn(Single.just(mPopularMoviesMocked));

        mMoviesMvpPresenter.getPopularMovies();
        verify(mMoviesMvpView).onGettingMovies(true);
        mTestScheduler.triggerActions();

        verify(mMovieMvpDataManager, times(1)).getPopularMovies();
        verify(mMoviesMvpView, times(1)).onMoviesReady(mPopularMoviesMocked);
        verify(mMoviesMvpView).onGettingMovies(false);
    }

    @Test
    public void deliveryToView_whenGetPopularMoviesThrowAError_failed() {
        when(mMovieMvpDataManager.getPopularMovies())
                .thenReturn(Single.<List<Movie>>error(new Exception("Mocked erro!")));

        mMoviesMvpPresenter.getPopularMovies();
        verify(mMoviesMvpView).onGettingMovies(true);
        mTestScheduler.triggerActions();

        verify(mMovieMvpDataManager, times(1)).getPopularMovies();
        verify(mMoviesMvpView, never()).onMoviesReady(anyListOf(Movie.class));
        verify(mMoviesMvpView, times(1)).onGetMoviesFailed();
        verify(mMoviesMvpView).onGettingMovies(false);
    }

    @Test
    public void deliveryToView_whenGetTopRatedMovies_success() {
        when(mMovieMvpDataManager.getTopRatedMovies()).thenReturn(Single.just(mTopRatedMoviesMocked));

        mMoviesMvpPresenter.getTopRatedMovies();
        verify(mMoviesMvpView).onGettingMovies(true);
        mTestScheduler.triggerActions();

        verify(mMovieMvpDataManager, times(1)).getTopRatedMovies();
        verify(mMoviesMvpView, times(1)).onMoviesReady(mTopRatedMoviesMocked);
        verify(mMoviesMvpView).onGettingMovies(false);
    }

    @Test
    public void deliveryToView_whenGetTopRatedMoviesThrowAError_failed() {
        when(mMovieMvpDataManager.getTopRatedMovies())
                .thenReturn(Single.<List<Movie>>error(new Exception("Mocked erro!")));

        mMoviesMvpPresenter.getTopRatedMovies();
        verify(mMoviesMvpView).onGettingMovies(true);
        mTestScheduler.triggerActions();

        verify(mMovieMvpDataManager, times(1)).getTopRatedMovies();
        verify(mMoviesMvpView, never()).onMoviesReady(anyListOf(Movie.class));
        verify(mMoviesMvpView, times(1)).onGetMoviesFailed();
        verify(mMoviesMvpView).onGettingMovies(false);
    }

    @Test
    public void whenGetLovedMovies_callGetLovedMoviesOfDataManager_success() {
        mMoviesMvpPresenter.getLovedMovies();
        mTestScheduler.triggerActions();

        verify(mMovieMvpDataManager).getLovedMovies();
    }

    @Test
    public void whenGetLovedMovies_callOnGettingMoviesOfView_success() {
        mMoviesMvpPresenter.getLovedMovies();
        verify(mMoviesMvpView).onGettingMovies(true);
        mTestScheduler.triggerActions();
        verify(mMoviesMvpView).onGettingMovies(false);
    }

    @Test
    public void whenGetLovedMovies_callOnMoviesReadyOfView_success() {
        mMoviesMvpPresenter.getLovedMovies();
        mTestScheduler.triggerActions();
        verify(mMoviesMvpView).onMoviesReady(mLovedMoviesMocked);
    }

    @Test
    public void whenGetLovedMovies_andDataManagerError_callOnGetMoviesFailedOfView_success() {
        when(mMovieMvpDataManager.getLovedMovies()).thenReturn(Single.<List<Movie>>error(new Exception()));

        mMoviesMvpPresenter.getLovedMovies();
        mTestScheduler.triggerActions();
        verify(mMoviesMvpView).onGetMoviesFailed();
    }

}
