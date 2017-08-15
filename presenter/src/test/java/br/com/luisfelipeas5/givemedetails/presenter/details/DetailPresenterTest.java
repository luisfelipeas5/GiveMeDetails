package br.com.luisfelipeas5.givemedetails.presenter.details;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.SchedulerProvider;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.TestSchedulerProvider;
import br.com.luisfelipeas5.givemedetails.view.details.MovieMvpView;
import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DetailPresenterTest {

    private static final String MOVIE_ID_MOCKED = "MOVIE_ID_MOCKED";
    private MovieDetailMvpPresenter mMovieDetailMvpPresenter;
    @Mock
    private MovieMvpView mMovieMvpView;
    @Mock
    private MovieMvpDataManager mMovieMvpDataManager;
    private TestScheduler mTestScheduler;
    @Mock
    private Movie mMovieMocked;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mMovieMocked.setId(MOVIE_ID_MOCKED);

        mTestScheduler = new TestScheduler();
        SchedulerProvider schedulerProvider = new TestSchedulerProvider(mTestScheduler);
        mMovieDetailMvpPresenter = new MovieDetailPresenter(mMovieMvpDataManager, schedulerProvider);
        mMovieDetailMvpPresenter.attach(mMovieMvpView);
    }

    @Test
    public void whenGetMovie_callDataManagerAndView_success() {
        when(mMovieMvpDataManager.getMovie(mMovieMocked.getId()))
                .thenReturn(Single.just(mMovieMocked));

        String movieId = mMovieMocked.getId();
        mMovieDetailMvpPresenter.getMovie(movieId);
        mTestScheduler.triggerActions();

        verify(mMovieMvpDataManager).getMovie(movieId);
        verify(mMovieMvpView).onMovieReady(mMovieMocked);
    }

    @Test
    public void whenGetMovie_callDataManagerAndView_failed() {
        when(mMovieMvpDataManager.getMovie(mMovieMocked.getId()))
                .thenReturn(Single.<Movie>error(new Exception("Mocked exception")));

        String movieId = mMovieMocked.getId();
        mMovieDetailMvpPresenter.getMovie(movieId);
        mTestScheduler.triggerActions();

        verify(mMovieMvpDataManager).getMovie(movieId);
        verify(mMovieMvpView).onGetMovieFailed();
    }

}
