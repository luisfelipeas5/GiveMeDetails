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
    private static final String MOVIE_ID_MOCKED_1 = "Movie id mocked 1";

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

        MovieTMDb movie1 = new MovieTMDb();
        movie1.setId(MOVIE_ID_MOCKED_1);
        movieDao.insert(movie1);
    }

    @Test
    public void whenInsertMovie_withIdValid_success() {
        MovieTMDb movieMocked = new MovieTMDb();
        movieMocked.setId("Movie Id Mocked" + System.currentTimeMillis());

        long id = MovieContentProvider.insert(mContentResolver, movieMocked);
        Assert.assertTrue(id > 0);
    }

}
