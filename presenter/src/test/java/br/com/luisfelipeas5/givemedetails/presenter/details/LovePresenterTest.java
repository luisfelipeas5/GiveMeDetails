package br.com.luisfelipeas5.givemedetails.presenter.details;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.SchedulerProvider;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.TestSchedulerProvider;
import br.com.luisfelipeas5.givemedetails.view.details.LoveMvpView;
import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LovePresenterTest {

    private static final String MOVIE_ID_MOCKED = "Movie Id Mocked";

    private LoveMvpPresenter mLoveMvpPresenter;
    private TestScheduler mTestScheduler;

    @Mock
    private MovieMvpDataManager mMovieMvpDataManager;
    @Mock
    private LoveMvpView mLoveMvpView;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(mMovieMvpDataManager.toggleMovieLove(MOVIE_ID_MOCKED)).thenReturn(Single.just(true));
        when(mMovieMvpDataManager.isMovieLoved(MOVIE_ID_MOCKED)).thenReturn(Single.just(true));
        when(mLoveMvpView.getMovieId()).thenReturn(MOVIE_ID_MOCKED);

        mTestScheduler = new TestScheduler();

        SchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);
        mLoveMvpPresenter = new LovePresenter(testSchedulerProvider, mMovieMvpDataManager);
        mLoveMvpPresenter.attach(mLoveMvpView);
    }

    @Test
    public void whenMovieIdReady_callIsLovedOfDataManager_success() {
        mLoveMvpPresenter.onMovieIdReady(MOVIE_ID_MOCKED);
        verify(mMovieMvpDataManager).isMovieLoved(MOVIE_ID_MOCKED);
    }

    @Test
    public void whenMovieIdReady_callOnMovieLovedTrueOfView_success() {
        when(mMovieMvpDataManager.isMovieLoved(MOVIE_ID_MOCKED)).thenReturn(Single.just(true));
        mLoveMvpPresenter.onMovieIdReady(MOVIE_ID_MOCKED);
        mTestScheduler.triggerActions();

        verify(mLoveMvpView).onMovieLoved(true);
    }

    @Test
    public void whenMovieIdReady_callOnMovieLovedFalseOfView_success() {
        when(mMovieMvpDataManager.isMovieLoved(MOVIE_ID_MOCKED)).thenReturn(Single.just(false));
        mLoveMvpPresenter.onMovieIdReady(MOVIE_ID_MOCKED);
        mTestScheduler.triggerActions();

        verify(mLoveMvpView).onMovieLoved(false);
    }

    @Test
    public void whenLoveMovieById_callDataManager_success() {
        mLoveMvpPresenter.loveMovieById(MOVIE_ID_MOCKED);
        verify(mMovieMvpDataManager).toggleMovieLove(MOVIE_ID_MOCKED);
    }

    @Test
    public void whenLoveMovieById_andDataManagerReturnTrue_callOnMovieLovedOfView_success() {
        mLoveMvpPresenter.loveMovieById(MOVIE_ID_MOCKED);
        mTestScheduler.triggerActions();

        verify(mLoveMvpView).onMovieLoved(true);
    }

    @Test
    public void whenLoveMovieById_andDataManagerReturnFalse_callOnMovieLovedOfView_success() {
        when(mMovieMvpDataManager.toggleMovieLove(MOVIE_ID_MOCKED)).thenReturn(Single.just(false));
        mLoveMvpPresenter.loveMovieById(MOVIE_ID_MOCKED);
        mTestScheduler.triggerActions();

        verify(mLoveMvpView).onMovieLoved(false);
    }

    @Test
    public void whenLoveMovieById_andDataManagerReturnError_callOnLoveFailedOfView_success() {
        when(mMovieMvpDataManager.toggleMovieLove(MOVIE_ID_MOCKED)).thenReturn(Single.<Boolean>error(new Exception()));
        mLoveMvpPresenter.loveMovieById(MOVIE_ID_MOCKED);
        mTestScheduler.triggerActions();

        verify(mLoveMvpView).onLoveFailed();
        verify(mLoveMvpView, never()).onMovieLoved(anyBoolean());
    }

    @Test
    public void whenLoveMovieById_callOnIsLovingOfView_success() {
        mLoveMvpPresenter.loveMovieById(MOVIE_ID_MOCKED);
        verify(mLoveMvpView).onIsLovingMovie(true);
        mTestScheduler.triggerActions();
        verify(mLoveMvpView).onIsLovingMovie(false);
    }

}
