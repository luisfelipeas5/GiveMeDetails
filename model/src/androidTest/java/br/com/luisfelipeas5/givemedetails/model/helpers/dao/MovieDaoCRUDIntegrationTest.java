package br.com.luisfelipeas5.givemedetails.model.helpers.dao;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.luisfelipeas5.givemedetails.model.daos.MovieDao;
import br.com.luisfelipeas5.givemedetails.model.databases.MovieCacheDatabase;
import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieTMDb;

@RunWith(AndroidJUnit4.class)
public class MovieDaoCRUDIntegrationTest {
    private MovieCacheDatabase mMovieCacheDatabase;

    @Before
    public void setUp() {
        Context applicationContext = InstrumentationRegistry.getTargetContext();

        mMovieCacheDatabase = Room
                .inMemoryDatabaseBuilder(applicationContext, MovieCacheDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() {
        mMovieCacheDatabase.close();
    }

    @Test
    public void whenInsertMovie_withIdValid_success() {
        MovieTMDb movieMocked = new MovieTMDb();
        movieMocked.setId("Movie Id Mocked" + System.currentTimeMillis());
        MovieDao movieDao = mMovieCacheDatabase.getMovieDao();
        long id = movieDao.insert(movieMocked);
        Assert.assertTrue(id > 0);
    }

    @Test
    public void whenGetMovieById_withIdExist_success() {
        MovieDao movieDao = mMovieCacheDatabase.getMovieDao();
        String id = "Movie Id Mocked" + System.currentTimeMillis();

        final MovieTMDb movieMocked = new MovieTMDb();
        movieMocked.setId(id);
        movieDao.insert(movieMocked);

        MovieTMDb movieById = movieDao.getMovieById(id);
        Assert.assertEquals(id, movieById.getId());
    }

    @Test
    public void whenGetMovieById_withIdNotExist_success() {
        MovieDao movieDao = mMovieCacheDatabase.getMovieDao();
        String id = "Movie Id Mocked" + System.currentTimeMillis();
        MovieTMDb movieById = movieDao.getMovieById(id);
        Assert.assertNull(movieById);
    }

    @Test
    public void whenGetMovieByIdCount_withIdExist_success() {
        MovieDao movieDao = mMovieCacheDatabase.getMovieDao();
        String id = "Movie Id Mocked" + System.currentTimeMillis();

        final MovieTMDb movieMocked = new MovieTMDb();
        movieMocked.setId(id);
        movieDao.insert(movieMocked);

        int movieByIdCount = movieDao.getMovieByIdCount(id);
        Assert.assertEquals(1, movieByIdCount);
    }

    @Test
    public void whenGetMovieByIdCount_withIdNotExist_success() {
        MovieDao movieDao = mMovieCacheDatabase.getMovieDao();
        String id = "Movie Id Mocked" + System.currentTimeMillis();

        int movieByIdCount = movieDao.getMovieByIdCount(id);
        Assert.assertEquals(0, movieByIdCount);
    }

}
