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

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.daos.MovieDao;
import br.com.luisfelipeas5.givemedetails.model.databases.MovieDatabase;
import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieLove;
import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieTMDb;

@RunWith(AndroidJUnit4.class)
public class LoveMovieContentProviderIntegrationTest {

    private ContentResolver mContentResolver;

    private static final String MOVIE_ID_MOCKED = "Movie id mocked";
    private static final String MOVIE_ID_MOCKED_1 = "Movie id mocked 1";
    private MovieLove mMovieLove;

    @Before
    public void setUp() {
        Context applicationContext = InstrumentationRegistry.getTargetContext();
        MovieDatabase movieDatabase = Room
                .inMemoryDatabaseBuilder(applicationContext, MovieDatabase.class)
                .allowMainThreadQueries()
                .build();
        MovieContentProvider.setMovieDatabase(movieDatabase);

        mContentResolver = applicationContext.getContentResolver();

        mMovieLove = new MovieLove();
        mMovieLove.setMovieId(MOVIE_ID_MOCKED);

        MovieDao movieDao = movieDatabase.getMovieDao();
        MovieTMDb movie = new MovieTMDb();
        movie.setId(MOVIE_ID_MOCKED);
        movieDao.insert(movie);

        MovieTMDb movie1 = new MovieTMDb();
        movie1.setId(MOVIE_ID_MOCKED_1);
        movieDao.insert(movie1);
    }

    @Test
    public void whenInsertMovieLove_withTrue_success() {
        mMovieLove.setLoved(true);
        long movieLoveId = MovieContentProvider.insert(mContentResolver, mMovieLove);
        Assert.assertTrue(movieLoveId > 0);
    }

    @Test
    public void whenInsertMovieLove_withFalse_success() {
        mMovieLove.setLoved(false);
        long movieLoveId = MovieContentProvider.insert(mContentResolver, mMovieLove);
        Assert.assertTrue(movieLoveId > 0);
    }

    @Test
    public void whenIsLoved_afterInsertionTrue_success() {
        mMovieLove.setLoved(true);
        MovieContentProvider.insert(mContentResolver, mMovieLove);

        boolean isLoved = MovieContentProvider.isLoved(mContentResolver, MOVIE_ID_MOCKED);
        Assert.assertTrue(isLoved);
    }

    @Test
    public void whenIsLoved_afterInsertionFalse_success() {
        mMovieLove.setLoved(false);
        MovieContentProvider.insert(mContentResolver, mMovieLove);

        boolean isLoved = MovieContentProvider.isLoved(mContentResolver, MOVIE_ID_MOCKED);
        Assert.assertFalse(isLoved);
    }

    @Test
    public void whenIsLoved_withoutInsertion_success() {
        boolean isLoved = MovieContentProvider.isLoved(mContentResolver, MOVIE_ID_MOCKED);
        Assert.assertFalse(isLoved);
    }

    @Test
    public void whenGetLovedMovies_withTrue_success() {
        mMovieLove.setLoved(true);
        MovieContentProvider.insert(mContentResolver, mMovieLove);
        mMovieLove.setMovieId(MOVIE_ID_MOCKED_1);
        mMovieLove.setLoved(true);
        MovieContentProvider.insert(mContentResolver, mMovieLove);

        List<MovieTMDb> lovedMovies = MovieContentProvider.getLoved(mContentResolver);
        Assert.assertTrue(lovedMovies.size() == 2);
    }

}
