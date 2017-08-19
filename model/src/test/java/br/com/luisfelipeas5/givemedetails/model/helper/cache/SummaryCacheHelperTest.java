package br.com.luisfelipeas5.givemedetails.model.helper.cache;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import br.com.luisfelipeas5.givemedetails.model.daos.MovieDao;
import br.com.luisfelipeas5.givemedetails.model.databases.MovieCacheDatabase;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieCacheHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieCacheMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieTMDb;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.when;

public class SummaryCacheHelperTest {

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
        when(mMovieTMDb.getTitle()).thenReturn("Movie title mocked");
        when(mMovieTMDb.getOriginalTitle()).thenReturn("Movie original title mocked");
        when(mMovieTMDb.getOverview()).thenReturn("Movie overview mocked");
        when(mMovieTMDb.getReleaseDate()).thenReturn("2017-07-05");
        when(mMovieTMDb.getReleaseDateAsDate()).thenReturn(new Date());

        when(mMovieCacheDatabase.getMovieDao()).thenReturn(mMovieDao);
        when(mMovieDao.getMovieById(MOVIE_ID_MOCKED)).thenReturn(mMovieTMDb);
        when(mMovieDao.getMovieByIdCount(MOVIE_ID_MOCKED)).thenReturn(1);

        mMovieCacheMvpHelper = new MovieCacheHelper(mMovieCacheDatabase);
    }

    @After
    public void closeDb() {
        mMovieCacheDatabase.close();
    }

    @Test
    public void whenHasMovieSummaryOnCache_returnTrue_success() {
        Single<Boolean> hasSummarySingle = mMovieCacheMvpHelper.hasMovieSummaryOnCache(MOVIE_ID_MOCKED);
        TestObserver<Boolean> test = hasSummarySingle.test();
        test.assertComplete();
        test.assertValue(true);
        test.assertNoErrors();
    }

    @Test
    public void whenHasMovieSummaryOnCache_movieWithoutTitle_returnFalse_success() {
        when(mMovieTMDb.getTitle()).thenReturn(null);

        Single<Boolean> hasSummarySingle = mMovieCacheMvpHelper.hasMovieSummaryOnCache(MOVIE_ID_MOCKED);
        TestObserver<Boolean> test = hasSummarySingle.test();
        test.assertComplete();
        test.assertValue(false);
        test.assertNoErrors();
    }

    @Test
    public void whenHasMovieSummaryOnCache_movieWithoutOverview_returnFalse_success() {
        when(mMovieTMDb.getOverview()).thenReturn(null);

        Single<Boolean> hasSummarySingle = mMovieCacheMvpHelper.hasMovieSummaryOnCache(MOVIE_ID_MOCKED);
        TestObserver<Boolean> test = hasSummarySingle.test();
        test.assertComplete();
        test.assertValue(false);
        test.assertNoErrors();
    }

    @Test
    public void whenHasMovieSummaryOnCache_movieWithoutOriginalTitle_returnFalse_success() {
        when(mMovieTMDb.getOriginalTitle()).thenReturn(null);

        Single<Boolean> hasSummarySingle = mMovieCacheMvpHelper.hasMovieSummaryOnCache(MOVIE_ID_MOCKED);
        TestObserver<Boolean> test = hasSummarySingle.test();
        test.assertComplete();
        test.assertValue(false);
        test.assertNoErrors();
    }

    @Test
    public void whenHasMovieSummaryOnCache_movieWithoutReleaseDate_returnFalse_success() {
        when(mMovieTMDb.getReleaseDateAsDate()).thenReturn(null);

        Single<Boolean> hasSummarySingle = mMovieCacheMvpHelper.hasMovieSummaryOnCache(MOVIE_ID_MOCKED);
        TestObserver<Boolean> test = hasSummarySingle.test();
        test.assertComplete();
        test.assertValue(false);
        test.assertNoErrors();
    }

}
