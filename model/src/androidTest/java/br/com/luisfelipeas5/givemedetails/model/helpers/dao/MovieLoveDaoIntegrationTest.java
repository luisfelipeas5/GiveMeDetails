package br.com.luisfelipeas5.givemedetails.model.helpers.dao;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.luisfelipeas5.givemedetails.model.daos.LoveDao;
import br.com.luisfelipeas5.givemedetails.model.daos.MovieDao;
import br.com.luisfelipeas5.givemedetails.model.databases.MovieDatabase;
import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieLove;
import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieTMDb;

@RunWith(AndroidJUnit4.class)
public class MovieLoveDaoIntegrationTest {

    private static final String MOVIE_ID_MOCKED = "Movie id mocked";
    private LoveDao mLoveDao;
    private MovieLove mMovieLove;

    @Before
    public void setUp() {
        Context applicationContext = InstrumentationRegistry.getTargetContext();

        mMovieLove = new MovieLove();
        mMovieLove.setMovieId(MOVIE_ID_MOCKED);

        MovieDatabase movieDatabase = Room
                .inMemoryDatabaseBuilder(applicationContext, MovieDatabase.class)
                .allowMainThreadQueries()
                .build();
        mLoveDao = movieDatabase.getLoveDao();

        MovieDao movieDao = movieDatabase.getMovieDao();
        MovieTMDb movie = new MovieTMDb();
        movie.setId(MOVIE_ID_MOCKED);
        movieDao.insert(movie);
    }

    @Test
    public void whenInsertMovieLove_withTrue_success() {
        mMovieLove.setLoved(true);
        long movieLoveId = mLoveDao.insert(mMovieLove);
        Assert.assertTrue(movieLoveId > 0);
    }

    @Test
    public void whenInsertMovieLove_withFalse_success() {
        mMovieLove.setLoved(false);
        long movieLoveId = mLoveDao.insert(mMovieLove);
        Assert.assertTrue(movieLoveId > 0);
    }

    @Test
    public void whenIsLoved_afterInsertionTrue_success() {
        mMovieLove.setLoved(true);
        mLoveDao.insert(mMovieLove);

        boolean isLoved = mLoveDao.isLoved(MOVIE_ID_MOCKED);
        Assert.assertTrue(isLoved);
    }

    @Test
    public void whenIsLoved_afterInsertionFalse_success() {
        mMovieLove.setLoved(false);
        mLoveDao.insert(mMovieLove);

        boolean isLoved = mLoveDao.isLoved(MOVIE_ID_MOCKED);
        Assert.assertFalse(isLoved);
    }

    @Test
    public void whenIsLoved_withoutInsertion_success() {
        boolean isLoved = mLoveDao.isLoved(MOVIE_ID_MOCKED);
        Assert.assertFalse(isLoved);
    }

}
