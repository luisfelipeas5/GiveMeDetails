package br.com.luisfelipeas5.givemedetails.presenter.details;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.SchedulerProvider;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.TestSchedulerProvider;
import br.com.luisfelipeas5.givemedetails.view.details.SocialMvpView;
import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SocialPresenterTest {

    private SocialMvpPresenter mPresenter;
    private TestScheduler mTestScheduler;

    @Mock
    public Movie mMovieMocked;
    @Mock
    public SocialMvpView mView;
    @Mock
    public MovieMvpDataManager mDataManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(mMovieMocked.getId()).thenReturn("Movie Id mocked");

        mTestScheduler = new TestScheduler();
        SchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);

        mPresenter = new SocialPresenter(testSchedulerProvider, mDataManager);
        mPresenter.attach(mView);
    }

    @Test
    public void whenGetSocial_callViewAndDataManager_success() {
        when(mDataManager.getMovieSocial(mMovieMocked.getId())).thenReturn(Single.just(mMovieMocked));

        mPresenter.getSocialById(mMovieMocked.getId());
        mTestScheduler.triggerActions();

        verify(mView).onMovieSocialReady(mMovieMocked);
        verify(mView, never()).onMovieSocialFailed();
        verify(mDataManager).getMovieSocial(mMovieMocked.getId());
    }

    @Test
    public void whenGetSocial_callViewAndDataManager_failed() {
        when(mDataManager.getMovieSocial(mMovieMocked.getId()))
                .thenReturn(Single.<Movie>error(new Exception("Exception mocked")));

        mPresenter.getSocialById(mMovieMocked.getId());
        mTestScheduler.triggerActions();

        verify(mView, never()).onMovieSocialReady(mMovieMocked);
        verify(mView).onMovieSocialFailed();
        verify(mDataManager).getMovieSocial(mMovieMocked.getId());
    }

}
