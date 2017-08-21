package br.com.luisfelipeas5.givemedetails.model.helper.cache;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.luisfelipeas5.givemedetails.model.daos.MovieDao;
import br.com.luisfelipeas5.givemedetails.model.databases.MovieCacheDatabase;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieCacheHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieCacheMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieTMDb;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.when;

public class SocialCacheHelperTest {
    private static final String MOVIE_ID_MOCKED = "Movie Id Mocked";
    private MovieCacheMvpHelper mMovieCacheMvpHelper;
    @Mock
    private MovieCacheDatabase mMovieCacheDatabase;
    @Mock
    private MovieDao mMovieDao;
    @Mock
    private MovieTMDb mMovieTMDb;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(mMovieTMDb.getId()).thenReturn(MOVIE_ID_MOCKED);
        when(mMovieTMDb.getVoteAverage()).thenReturn(1.0);
        when(mMovieTMDb.getVoteCount()).thenReturn(1000L);
        when(mMovieTMDb.getPopularity()).thenReturn(15.5f);

        when(mMovieCacheDatabase.getMovieDao()).thenReturn(mMovieDao);
        when(mMovieDao.getMovieById(MOVIE_ID_MOCKED)).thenReturn(mMovieTMDb);
        when(mMovieDao.getMovieByIdCount(MOVIE_ID_MOCKED)).thenReturn(1);

        mMovieCacheMvpHelper = new MovieCacheHelper(mMovieCacheDatabase);
    }

    @Test
    public void whenHasSocialOnCache_returnTrue_success() {
        Single<Boolean> hasMovieSocialOnCacheSingle = mMovieCacheMvpHelper.hasMovieSocialOnCache(MOVIE_ID_MOCKED);
        TestObserver<Boolean> testObserver = hasMovieSocialOnCacheSingle.test();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValue(true);
    }

    @Test
    public void whenHasSocialOnCache_withMovieWithoutVoteAverage_returnFail() {
        when(mMovieTMDb.getVoteAverage()).thenReturn(null);

        Single<Boolean> hasMovieSocialOnCacheSingle = mMovieCacheMvpHelper.hasMovieSocialOnCache(MOVIE_ID_MOCKED);
        TestObserver<Boolean> testObserver = hasMovieSocialOnCacheSingle.test();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValue(false);
    }

    @Test
    public void whenHasSocialOnCache_withMovieWithoutVoteCount_returnFail() {
        when(mMovieTMDb.getVoteCount()).thenReturn(null);

        Single<Boolean> hasMovieSocialOnCacheSingle = mMovieCacheMvpHelper.hasMovieSocialOnCache(MOVIE_ID_MOCKED);
        TestObserver<Boolean> testObserver = hasMovieSocialOnCacheSingle.test();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValue(false);
    }

    @Test
    public void whenHasSocialOnCache_withMovieWithoutPopularity_returnFail() {
        when(mMovieTMDb.getPopularity()).thenReturn(null);

        Single<Boolean> hasMovieSocialOnCacheSingle = mMovieCacheMvpHelper.hasMovieSocialOnCache(MOVIE_ID_MOCKED);
        TestObserver<Boolean> testObserver = hasMovieSocialOnCacheSingle.test();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValue(false);
    }

}
