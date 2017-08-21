package br.com.luisfelipeas5.givemedetails.model.contentproviders;

import android.arch.persistence.room.Room;
import android.content.ContentResolver;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.luisfelipeas5.givemedetails.model.daos.MovieDao;
import br.com.luisfelipeas5.givemedetails.model.databases.MovieDatabase;
import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieTMDb;

@RunWith(AndroidJUnit4.class)
public class MovieContentProviderIntegrationTest {

    private ContentResolver mContentResolver;

    private static final String MOVIE_ID_MOCKED = "Movie id mocked";

    @Before
    public void setUp() {
        Context applicationContext = InstrumentationRegistry.getTargetContext();
        MovieDatabase movieDatabase = Room
                .inMemoryDatabaseBuilder(applicationContext, MovieDatabase.class)
                .allowMainThreadQueries()
                .build();
        MovieContentProvider.setMovieDatabase(movieDatabase);

        mContentResolver = applicationContext.getContentResolver();

        MovieDao movieDao = movieDatabase.getMovieDao();
        MovieTMDb movie = new MovieTMDb();
        movie.setId(MOVIE_ID_MOCKED);
        movieDao.insert(movie);
    }

    @Test
    public void whenInsertMovie_withIdValid_success() {
        MovieTMDb movieMocked = new MovieTMDb();
        movieMocked.setId("Movie Id Mocked" + System.currentTimeMillis());

        long id = MovieContentProvider.insert(mContentResolver, movieMocked);
        Assert.assertTrue(id > 0);
    }

    @Test
    public void whenGetMovieById_withIdExist_success() {
        MovieTMDb movieById = MovieContentProvider.getMovieById(mContentResolver, MOVIE_ID_MOCKED);
        Assert.assertNotNull(movieById);
        Assert.assertEquals(MOVIE_ID_MOCKED, movieById.getId());
    }

    @Test
    public void whenGetMovieById_withIdNotExist_success() {
        String id = "Movie Id Mocked" + System.currentTimeMillis();
        MovieTMDb movieById = MovieContentProvider.getMovieById(mContentResolver, id);
        Assert.assertNull(movieById);
    }

    @Test
    public void whenGetMovieByIdCount_withIdExist_success() {
        int movieByIdCount = MovieContentProvider.getMovieByIdCount(mContentResolver, MOVIE_ID_MOCKED);
        Assert.assertEquals(1, movieByIdCount);
    }

    @Test
    public void whenGetMovieByIdCount_withIdNotExist_success() {
        String id = "Movie Id Mocked" + System.currentTimeMillis();
        int movieByIdCount = MovieContentProvider.getMovieByIdCount(mContentResolver, id);
        Assert.assertEquals(0, movieByIdCount);
    }

}
