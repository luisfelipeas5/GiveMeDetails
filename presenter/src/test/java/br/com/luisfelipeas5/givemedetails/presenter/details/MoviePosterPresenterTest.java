package br.com.luisfelipeas5.givemedetails.presenter.details;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.model.Movie;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.SchedulerProvider;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.TestSchedulerProvider;
import br.com.luisfelipeas5.givemedetails.view.details.MoviePosterMvpView;
import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MoviePosterPresenterTest {

    private static final String MOVIE_ID_MOCKED = "MOVIE_ID_MOCKED";
    private MoviePosterMvpPresenter mMovieDetailMvpPresenter;
    @Mock
    private MoviePosterMvpView mMovieMvpView;
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
        mMovieDetailMvpPresenter = new MoviePosterPresenter(mMovieMvpDataManager, schedulerProvider);
        mMovieDetailMvpPresenter.attach(mMovieMvpView);
    }

    @Test
    public void whenGetMoviePosterUrl_callDataManagerAndView_success() {
        int width = 780;
        String posterUrl = "Fake poster url";
        when(mMovieMvpDataManager.getMoviePosterUrl(width, MOVIE_ID_MOCKED))
                .thenReturn(Single.just(posterUrl));

        mMovieDetailMvpPresenter.getMoviePosterUrl(MOVIE_ID_MOCKED, width);
        mTestScheduler.triggerActions();

        verify(mMovieMvpDataManager).getMoviePosterUrl(width, MOVIE_ID_MOCKED);
        verify(mMovieMvpView).onMoviePosterUrlReady(posterUrl);
    }

    @Test
    public void whenGetMoviePosterUrl_callDataManagerAndView_failed() {
        int width = 780;
        when(mMovieMvpDataManager.getMoviePosterUrl(width, MOVIE_ID_MOCKED))
                .thenReturn(Single.<String>error(new Exception("Movie poster wasn't find.")));

        mMovieDetailMvpPresenter.getMoviePosterUrl(MOVIE_ID_MOCKED, width);
        mTestScheduler.triggerActions();

        verify(mMovieMvpDataManager).getMoviePosterUrl(width, MOVIE_ID_MOCKED);
        verify(mMovieMvpView).onGetMoviePosterUrlFailed();
    }

    @Test
    public void whenGetMovieTitle_callDataManagerAndView_success() {
        String movieTitle = "Movie title mocked";
        when(mMovieMvpDataManager.getMovieTitle(MOVIE_ID_MOCKED)).thenReturn(Single.just(movieTitle));

        mMovieDetailMvpPresenter.getMovieTitle(MOVIE_ID_MOCKED);
        mTestScheduler.triggerActions();

        verify(mMovieMvpDataManager).getMovieTitle(MOVIE_ID_MOCKED);
        verify(mMovieMvpView).onGetMovieTitleReady(movieTitle);
    }

    @Test
    public void whenGetMovieTitle_callDataManagerAndView_failed() {
        when(mMovieMvpDataManager.getMovieTitle(MOVIE_ID_MOCKED))
                .thenReturn(Single.<String>error(new Exception("Movie title wasn't find.")));

        mMovieDetailMvpPresenter.getMovieTitle(MOVIE_ID_MOCKED);
        mTestScheduler.triggerActions();

        verify(mMovieMvpDataManager).getMovieTitle(MOVIE_ID_MOCKED);
        verify(mMovieMvpView).onGetMovieTitleFailed();
    }

}
