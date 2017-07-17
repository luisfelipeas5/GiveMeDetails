package br.com.luisfelipeas5.givemedetails.presenter.details;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.model.Movie;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.SchedulerProvider;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.TestSchedulerProvider;
import br.com.luisfelipeas5.givemedetails.view.details.SummaryMvpView;
import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SummaryPresenterTest {

    private SummaryMvpPresenter mSummaryMvpPresenter;
    private TestScheduler mTestScheduler;
    @Mock
    private MovieMvpDataManager mMovieMvpDataManager;
    @Mock
    private SummaryMvpView mSummaryMvpView;
    @Mock
    private Movie mMovie;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mTestScheduler = new TestScheduler();
        SchedulerProvider mSchedulerProvider = new TestSchedulerProvider(mTestScheduler);
        mSummaryMvpPresenter = new SummaryPresenter(mMovieMvpDataManager, mSchedulerProvider);
        mSummaryMvpPresenter.attach(mSummaryMvpView);
    }

    @Test
    public void whenGetMovieSummary_callViewAndDataManager_returnMovie_success() {
        String movieId = "Movie Id mocked";
        when(mMovieMvpDataManager.getMovieSummary(movieId)).thenReturn(Single.just(mMovie));

        mSummaryMvpPresenter.getSummary(movieId);
        mTestScheduler.triggerActions();

        verify(mMovieMvpDataManager).getMovieSummary(movieId);
        verify(mSummaryMvpView).onTitleReady(mMovie.getTitle());
        verify(mSummaryMvpView, never()).onSummaryFailed();
    }

    @Test
    public void whenGetMovieSummary_callViewAndDataManager_returnMovie_failed() {
        String movieId = "Movie Id mocked";
        when(mMovieMvpDataManager.getMovieSummary(movieId)).thenReturn(Single.<Movie>error(new Exception("Get movie summary failed")));

        mSummaryMvpPresenter.getSummary(movieId);
        mTestScheduler.triggerActions();

        verify(mMovieMvpDataManager).getMovieSummary(movieId);
        verify(mSummaryMvpView, never()).onTitleReady(mMovie.getTitle());
        verify(mSummaryMvpView).onSummaryFailed();
    }

}
