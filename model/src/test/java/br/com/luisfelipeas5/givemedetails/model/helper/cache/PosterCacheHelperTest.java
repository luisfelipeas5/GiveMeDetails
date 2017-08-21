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

public class PosterCacheHelperTest {

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
        when(mMovieTMDb.getPoster()).thenReturn("Poster mocked");
        when(mMovieTMDb.getTitle()).thenReturn("Movie title mocked");

        when(mMovieCacheDatabase.getMovieDao()).thenReturn(mMovieDao);
        when(mMovieDao.getMovieById(MOVIE_ID_MOCKED)).thenReturn(mMovieTMDb);
        when(mMovieDao.getMovieByIdCount(MOVIE_ID_MOCKED)).thenReturn(1);

        mMovieCacheMvpHelper = new MovieCacheHelper(mMovieCacheDatabase);
    }

    @Test
    public void whenHasMoviePosterOnCache_returnFalse_success() {
        when(mMovieTMDb.getPoster()).thenReturn(null);

        Single<Boolean> hasPosterSingle = mMovieCacheMvpHelper.hasMoviePosterOnCache(MOVIE_ID_MOCKED);
        TestObserver<Boolean> test = hasPosterSingle.test();
        test.assertComplete();
        test.assertValue(false);
        test.assertNoErrors();
    }

    @Test
    public void whenHasMoviePosterOnCache_returnTrue_success() {
        Single<Boolean> hasPosterSingle = mMovieCacheMvpHelper.hasMoviePosterOnCache(MOVIE_ID_MOCKED);
        TestObserver<Boolean> test = hasPosterSingle.test();
        test.assertComplete();
        test.assertValue(true);
        test.assertNoErrors();
    }

    @Test
    public void whenHasMovieTitleOnCache_returnFalse_success() {
        when(mMovieTMDb.getTitle()).thenReturn(null);

        Single<Boolean> hasTitleSingle = mMovieCacheMvpHelper.hasMovieTitleOnCache(MOVIE_ID_MOCKED);
        TestObserver<Boolean> test = hasTitleSingle.test();
        test.assertComplete();
        test.assertValue(false);
        test.assertNoErrors();
    }

    @Test
    public void whenHasMovieTitleOnCache_returnTrue_success() {
        Single<Boolean> hasTitleSingle = mMovieCacheMvpHelper.hasMovieTitleOnCache(MOVIE_ID_MOCKED);
        TestObserver<Boolean> test = hasTitleSingle.test();
        test.assertComplete();
        test.assertValue(true);
        test.assertNoErrors();
    }

}
