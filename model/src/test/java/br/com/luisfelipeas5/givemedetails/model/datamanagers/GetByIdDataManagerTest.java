package br.com.luisfelipeas5.givemedetails.model.datamanagers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieDataManager;
import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.helpers.DatabaseMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieApiMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieCacheMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetByIdDataManagerTest {
    private MovieMvpDataManager mMvpDataManager;

    @Mock
    private MovieApiMvpHelper mMovieApiMvpHelper;
    @Mock
    private MovieCacheMvpHelper mMovieCacheHelper;
    @Mock
    private DatabaseMvpHelper mDatabaseMvpHelper;
    @Mock
    private Movie mMovieMocked;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mMvpDataManager = new MovieDataManager(mMovieApiMvpHelper, mMovieCacheHelper, mDatabaseMvpHelper);

        when(mMovieCacheHelper.saveMovie(mMovieMocked)).thenReturn(Single.just(true));
        when(mMovieCacheHelper.clearCache()).thenReturn(Single.just(true));

        Observable<Movie> movieObservable = Observable.just(mMovieMocked);
        when(mMovieApiMvpHelper.getMovie(Matchers.isNotNull(String.class))).thenReturn(movieObservable);
        when(mMovieApiMvpHelper.getMovie(Matchers.matches(".{1,}"))).thenReturn(movieObservable);

        Observable<Movie> errorObservable = Observable.error(new Exception());
        when(mMovieApiMvpHelper.getMovie(Matchers.matches(" {1,}"))).thenReturn(errorObservable);
        when(mMovieApiMvpHelper.getMovie(Matchers.isNull(String.class))).thenReturn(errorObservable);
        when(mMovieApiMvpHelper.getMovie("")).thenReturn(errorObservable);
    }

    @Test
    public void whenGetMovie_ByValidId_returnMovie_success() {
        String movieId = "someMovieIdMocked";
        Single<Movie> movieObservable = mMvpDataManager.getMovie(movieId);

        TestObserver<Movie> testObserver = movieObservable.test();
        testObserver.assertNoErrors();
        testObserver.assertComplete();

        testObserver.assertValue(mMovieMocked);
        verify(mMovieApiMvpHelper).getMovie(movieId);
        verify(mMovieCacheHelper).saveMovie(mMovieMocked);
        verify(mMovieCacheHelper).clearCache();
    }

    @Test
    public void whenGetMovie_ByNullId_returnMovie_failed() {
        Single<Movie> movieObservable = mMvpDataManager.getMovie(null);
        Assert.assertNull(movieObservable);
        verify(mMovieApiMvpHelper, never()).getMovie(anyString());
        verify(mMovieApiMvpHelper, never()).getMovie(isNull(String.class));
    }

    @Test
    public void whenGetMovie_ByEmptyId_returnMovie_failed() {
        Single<Movie> movieObservable = mMvpDataManager.getMovie("");
        Assert.assertNull(movieObservable);
        verify(mMovieApiMvpHelper, never()).getMovie(anyString());
        verify(mMovieApiMvpHelper, never()).getMovie(isNull(String.class));
    }

    @Test
    public void whenGetMovie_ByManySpacesId_returnMovie_failed() {
        Single<Movie> movieObservable = mMvpDataManager.getMovie("               ");
        Assert.assertNull(movieObservable);
        verify(mMovieApiMvpHelper, never()).getMovie(anyString());
        verify(mMovieApiMvpHelper, never()).getMovie(isNull(String.class));
    }

}
