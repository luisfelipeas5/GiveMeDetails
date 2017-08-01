package br.com.luisfelipeas5.givemedetails.model.datamanagers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieDataManager;
import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieApiMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieCacheMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.model.Movie;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SummaryDataManagerTest {

    private MovieMvpDataManager mMovieMvpDataManager;
    @Mock
    private MovieApiMvpHelper mMovieApiMvpHelper;
    @Mock
    private MovieCacheMvpHelper mMovieCacheMvpHelper;
    @Mock
    private Movie mMovieMocked;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(mMovieMocked.getId()).thenReturn("MOVIE_ID_MOCKED");

        mMovieMvpDataManager = new MovieDataManager(mMovieApiMvpHelper, mMovieCacheMvpHelper);
    }

    @Test
    public void whenGetSummary_byId_returnMovie_withoutCache_success() {
        String movieMockedId = mMovieMocked.getId();
        when(mMovieCacheMvpHelper.hasMovieSummaryOnCache(movieMockedId)).thenReturn(Single.just(false));
        when(mMovieApiMvpHelper.getMovieSummary(movieMockedId)).thenReturn(Observable.just(mMovieMocked));

        Single<Movie> movieSummarySingle = mMovieMvpDataManager.getMovieSummary(movieMockedId);
        TestObserver<Movie> movieTestObserver = movieSummarySingle.test();
        movieTestObserver.assertValue(mMovieMocked);
        movieTestObserver.assertComplete();

        verify(mMovieApiMvpHelper).getMovieSummary(movieMockedId);
        verify(mMovieCacheMvpHelper).hasMovieSummaryOnCache(movieMockedId);
        verify(mMovieCacheMvpHelper, never()).getMovie(movieMockedId);
    }

    @Test
    public void whenGetSummary_byId_returnMovie_withCache_success() {
        String movieMockedId = mMovieMocked.getId();
        when(mMovieCacheMvpHelper.hasMovieSummaryOnCache(movieMockedId)).thenReturn(Single.just(true));
        when(mMovieCacheMvpHelper.getMovie(movieMockedId)).thenReturn(Single.just(mMovieMocked));

        Single<Movie> movieSummarySingle = mMovieMvpDataManager.getMovieSummary(movieMockedId);
        TestObserver<Movie> movieTestObserver = movieSummarySingle.test();
        movieTestObserver.assertValue(mMovieMocked);
        movieTestObserver.assertComplete();

        verify(mMovieApiMvpHelper, never()).getMovieSummary(movieMockedId);
        verify(mMovieCacheMvpHelper).hasMovieSummaryOnCache(movieMockedId);
        verify(mMovieCacheMvpHelper).getMovie(movieMockedId);
    }

}
