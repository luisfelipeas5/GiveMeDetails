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
    private static final String MOVIE_TITLE_MOCKED = "Title mocked";
    private static final String MOVIE_OVERVIEW_MOCKED = "Overview mocked";
    private static final String MOVIE_ORIGINAL_TITLE_MOCKED = "Original title mocked";
    private static final String MOVIE_RELEASE_DATE_MOCKED = "2017-01-01";
    private static final float MOVIE_POPULARITY_MOCKED = 1f;
    private static final long MOVIE_VOTE_COUNT_MOCKED = 1000L;
    private static final double MOVIE_VOTE_AVERAGE_MOCKED = 9.3;
    private static final String MOVIE_POSTER_SUFFIX_MOCKED = "Poster suffix mocked";

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
        movie.setTitle(MOVIE_TITLE_MOCKED);
        movie.setOverview(MOVIE_OVERVIEW_MOCKED);
        movie.setOriginalTitle(MOVIE_ORIGINAL_TITLE_MOCKED);
        movie.setReleaseDate(MOVIE_RELEASE_DATE_MOCKED);
        movie.setPopularity(MOVIE_POPULARITY_MOCKED);
        movie.setVoteCount(MOVIE_VOTE_COUNT_MOCKED);
        movie.setVoteAverage(MOVIE_VOTE_AVERAGE_MOCKED);
        movie.setPosterSuffix(MOVIE_POSTER_SUFFIX_MOCKED);
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
        Assert.assertEquals(MOVIE_TITLE_MOCKED, movieById.getTitle());
        Assert.assertEquals(MOVIE_OVERVIEW_MOCKED, movieById.getOverview());
        Assert.assertEquals(MOVIE_ORIGINAL_TITLE_MOCKED, movieById.getOriginalTitle());
        Assert.assertEquals(MOVIE_RELEASE_DATE_MOCKED, movieById.getReleaseDate());
        Assert.assertEquals(MOVIE_VOTE_AVERAGE_MOCKED, movieById.getVoteAverage(), 0);
        Assert.assertEquals(MOVIE_VOTE_COUNT_MOCKED, (long) movieById.getVoteCount());
        Assert.assertEquals(MOVIE_POPULARITY_MOCKED, movieById.getPopularity(), 0);
        Assert.assertEquals(MOVIE_POSTER_SUFFIX_MOCKED, movieById.getPosterSuffix());
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
