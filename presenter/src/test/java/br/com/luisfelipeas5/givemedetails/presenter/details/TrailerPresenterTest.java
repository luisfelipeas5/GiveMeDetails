package br.com.luisfelipeas5.givemedetails.presenter.details;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import br.com.luisfelipeas5.givemedetails.model.model.trailer.Trailer;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.SchedulerProvider;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.TestSchedulerProvider;
import br.com.luisfelipeas5.givemedetails.view.details.TrailersMvpView;
import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TrailerPresenterTest {

    private TrailerMvpPresenter mTrailerMvpPresenter;
    private TestScheduler mTestScheduler;

    @Mock
    private TrailersMvpView mTrailersMvpView;
    @Mock
    private MovieMvpDataManager mMovieMvpDataManager;
    @Mock
    private Movie mMovieMocked;

    @Mock private Trailer mTrailer0;
    @Mock private Trailer mTrailer1;
    @Mock private Trailer mTrailer2;
    @Mock private Trailer mTrailer3;
    private List<Trailer> mTrailers;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mTrailers = new LinkedList<>();
        mTrailers.add(mTrailer0);
        mTrailers.add(mTrailer1);
        mTrailers.add(mTrailer2);
        mTrailers.add(mTrailer3);

        when(mMovieMocked.getId()).thenReturn("Movie Id Mocked");
        when(mMovieMvpDataManager.getMovieTrailers(mMovieMocked.getId()))
                .thenReturn(Single.just(mTrailers));

        mTestScheduler = new TestScheduler();
        SchedulerProvider schedulerProvider = new TestSchedulerProvider(mTestScheduler);
        mTrailerMvpPresenter = new TrailerPresenter(schedulerProvider, mMovieMvpDataManager);
        mTrailerMvpPresenter.attach(mTrailersMvpView);
    }

    @Test
    public void whenGetTrailers_returnTrailers_success() {
        String movieId = mMovieMocked.getId();
        mTrailerMvpPresenter.getTrailers(movieId);
        mTestScheduler.triggerActions();

        verify(mMovieMvpDataManager).getMovieTrailers(movieId);
        verify(mTrailersMvpView).onTrailersReady(mTrailers);
        verify(mTrailersMvpView, never()).onGetTrailersFailed();
    }

    @Test
    public void whenGetTrailersPreview_returnTrailers_success() {
        String movieId = mMovieMocked.getId();
        mTrailerMvpPresenter.getTrailersPreview(movieId);
        mTestScheduler.triggerActions();

        verify(mMovieMvpDataManager).getMovieTrailers(movieId);
        verify(mTrailersMvpView).onTrailersReady(mTrailers.subList(0, 3));
        verify(mTrailersMvpView).showSeeAllTrailersButton();
        verify(mTrailersMvpView, never()).onGetTrailersFailed();
    }

    @Test
    public void whenGetTrailers_returnTrailers_failed() {
        when(mMovieMvpDataManager.getMovieTrailers(mMovieMocked.getId()))
                .thenReturn(Single.<List<Trailer>>error(new Exception("Exception mocked")));

        String movieId = mMovieMocked.getId();
        mTrailerMvpPresenter.getTrailers(movieId);
        mTestScheduler.triggerActions();

        verify(mMovieMvpDataManager).getMovieTrailers(movieId);
        verify(mTrailersMvpView, never()).onTrailersReady(anyListOf(Trailer.class));
        verify(mTrailersMvpView).onGetTrailersFailed();
    }

}
