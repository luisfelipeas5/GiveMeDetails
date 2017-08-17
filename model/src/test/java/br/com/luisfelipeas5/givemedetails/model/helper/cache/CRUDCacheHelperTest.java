package br.com.luisfelipeas5.givemedetails.model.helper.cache;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.luisfelipeas5.givemedetails.model.daos.MovieDao;
import br.com.luisfelipeas5.givemedetails.model.databases.MovieCacheDatabase;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieCacheHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieCacheMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieTMDb;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CRUDCacheHelperTest {

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

        when(mMovieCacheDatabase.getMovieDao()).thenReturn(mMovieDao);

        when(mMovieDao.getMovieById(MOVIE_ID_MOCKED)).thenReturn(mMovieTMDb);
        when(mMovieDao.getMovieByIdCount(MOVIE_ID_MOCKED)).thenReturn(1);
        when(mMovieDao.insert(mMovieTMDb)).thenReturn(1L);

        mMovieCacheMvpHelper = new MovieCacheHelper(mMovieCacheDatabase);
    }

    @Test
    public void whenSaveMovie_callInsertOfMovieDao_success() {
        Single<Boolean> saveMovieSingle = mMovieCacheMvpHelper.saveMovie(mMovieTMDb);
        TestObserver<Boolean> testObserver = saveMovieSingle.test();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValue(true);

        verify(mMovieDao).insert(mMovieTMDb);
    }

    @Test
    public void whenGetMovieById_callGetByIdOfMovieDao_success() {
        Single<Movie> movieSingle = mMovieCacheMvpHelper.getMovie(MOVIE_ID_MOCKED);
        TestObserver<Movie> testObserver = movieSingle.test();
        testObserver.assertValue(mMovieTMDb);
        testObserver.assertNoErrors();

        verify(mMovieDao).getMovieById(MOVIE_ID_MOCKED);
    }

}
