package br.com.luisfelipeas5.givemedetails.model.datamanagers;

import org.junit.Before;
import org.junit.Test;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetByIdDataManagerTest {
    private static final String MOVIE_ID_MOCKED = "Movie Id Mocked";
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

        when(mDatabaseMvpHelper.isLoved(MOVIE_ID_MOCKED)).thenReturn(Single.just(false));
        when(mDatabaseMvpHelper.getMovie(MOVIE_ID_MOCKED)).thenReturn(Single.just(mMovieMocked));

        when(mMovieCacheHelper.saveMovie(mMovieMocked)).thenReturn(Single.just(true));
        when(mMovieCacheHelper.clearCache()).thenReturn(Single.just(true));

        Observable<Movie> movieObservable = Observable.just(mMovieMocked);
        when(mMovieApiMvpHelper.getMovie(MOVIE_ID_MOCKED)).thenReturn(movieObservable);
    }

    @Test
    public void whenGetMovie_callGetMovieOfApiHelper_success() {
        Single<Movie> movieObservable = mMvpDataManager.getMovie(MOVIE_ID_MOCKED);

        TestObserver<Movie> testObserver = movieObservable.test();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValue(mMovieMocked);

        verify(mMovieApiMvpHelper).getMovie(MOVIE_ID_MOCKED);
    }

    @Test
    public void whenGetMovie_callSaveOfCacheHelper_success() {
        Single<Movie> movieObservable = mMvpDataManager.getMovie(MOVIE_ID_MOCKED);

        TestObserver<Movie> testObserver = movieObservable.test();
        testObserver.assertNoErrors();
        testObserver.assertComplete();

        testObserver.assertValue(mMovieMocked);
        verify(mMovieCacheHelper).saveMovie(mMovieMocked);
    }

    @Test
    public void whenGetMovie_callCacheOfCacheHelper_success() {
        Single<Movie> movieObservable = mMvpDataManager.getMovie(MOVIE_ID_MOCKED);

        TestObserver<Movie> testObserver = movieObservable.test();
        testObserver.assertNoErrors();
        testObserver.assertComplete();

        testObserver.assertValue(mMovieMocked);
        verify(mMovieCacheHelper).clearCache();
    }

    @Test
    public void whenGetMovie_callIsLovedOfDatabaseHelper_success() {
        Single<Movie> movieObservable = mMvpDataManager.getMovie(MOVIE_ID_MOCKED);

        TestObserver<Movie> testObserver = movieObservable.test();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValue(mMovieMocked);

        verify(mDatabaseMvpHelper).isLoved(MOVIE_ID_MOCKED);
    }

    @Test
    public void whenGetMovie_andMovieIsLoved_callGetMovieOfDatabase_success() {
        when(mDatabaseMvpHelper.isLoved(MOVIE_ID_MOCKED)).thenReturn(Single.just(true));

        Single<Movie> movieObservable = mMvpDataManager.getMovie(MOVIE_ID_MOCKED);
        TestObserver<Movie> testObserver = movieObservable.test();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValue(mMovieMocked);

        verify(mDatabaseMvpHelper).getMovie(MOVIE_ID_MOCKED);
    }

}
