package br.com.luisfelipeas5.givemedetails.model.datamanagers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieDataManager;
import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieApiMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieCacheMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import br.com.luisfelipeas5.givemedetails.model.model.trailer.Trailer;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TrailersDataManagerTest {

    private MovieMvpDataManager mMovieMvpDataManager;

    @Mock
    private MovieApiMvpHelper mMovieApiMvpHelper;
    @Mock
    private MovieCacheMvpHelper mMovieCacheHelper;
    @Mock
    private Movie mMovieMocked;
    @Mock
    private List<Trailer> mTrailers;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(mMovieMocked.getId()).thenReturn("Movie Mocked Id");
        when(mMovieApiMvpHelper.getTrailers(mMovieMocked.getId())).thenReturn(Observable.just(mTrailers));

        mMovieMvpDataManager = new MovieDataManager(mMovieApiMvpHelper, mMovieCacheHelper);
    }

    @Test
    public void whenGetVideos_returnVideos_success() {
        String movieId = mMovieMocked.getId();

        Single<List<Trailer>> trailersSingle = mMovieMvpDataManager.getMovieTrailers(movieId);
        TestObserver<List<Trailer>> testObserver = trailersSingle.test();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValue(mTrailers);

        verify(mMovieApiMvpHelper).getTrailers(movieId);
    }

    @Test
    public void whenGetVideos_withNullId_returnVideos_failed() {
        when(mMovieMocked.getId()).thenReturn(null);
        String movieId = mMovieMocked.getId();

        Single<List<Trailer>> trailersSingle = mMovieMvpDataManager.getMovieTrailers(movieId);
        Assert.assertNull(trailersSingle);

        verify(mMovieApiMvpHelper, never()).getTrailers(movieId);
    }

}
