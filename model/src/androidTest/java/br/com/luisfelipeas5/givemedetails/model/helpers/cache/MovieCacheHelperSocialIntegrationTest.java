package br.com.luisfelipeas5.givemedetails.model.helpers.cache;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import br.com.luisfelipeas5.givemedetails.model.databases.MovieCacheDatabase;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieCacheHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieCacheMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.model.MovieTMDb;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

@RunWith(AndroidJUnit4.class)
public class MovieCacheHelperSocialIntegrationTest {
    private MovieCacheMvpHelper mMovieCacheMvpHelper;
    private MovieCacheDatabase mMovieCacheDatabase;
    private MovieTMDb mMovie;

    @Before
    public void setUp() {
        Context applicationContext = InstrumentationRegistry.getTargetContext();

        mMovieCacheDatabase = Room
                .inMemoryDatabaseBuilder(applicationContext, MovieCacheDatabase.class)
                .allowMainThreadQueries()
                .build();
        mMovieCacheMvpHelper = new MovieCacheHelper(mMovieCacheDatabase);

        mMovie = new MovieTMDb();
        mMovie.setId("Movie Id Mocked " + System.currentTimeMillis());
        Random random = new Random();
        mMovie.setVoteAverage(random.nextDouble());
        mMovie.setVoteCount(random.nextLong());
        mMovie.setPopularity(random.nextFloat());

        mMovieCacheMvpHelper.saveMovie(mMovie);
    }

    @After
    public void closeDb() {
        mMovieCacheDatabase.close();
    }

    @Test
    public void whenHasSocialOnCache_returnTrue_success() {
        Single<Boolean> hasMovieSocialOnCacheSingle = mMovieCacheMvpHelper.hasMovieSocialOnCache(mMovie.getId());
        TestObserver<Boolean> testObserver = hasMovieSocialOnCacheSingle.test();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValue(true);
    }

    @Test
    public void whenHasSocialOnCache_withMovieWithoutVoteAverage_returnFail() {
        mMovie.setVoteAverage(null);
        mMovieCacheMvpHelper.saveMovie(mMovie);

        Single<Boolean> hasMovieSocialOnCacheSingle = mMovieCacheMvpHelper.hasMovieSocialOnCache(mMovie.getId());
        TestObserver<Boolean> testObserver = hasMovieSocialOnCacheSingle.test();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValue(false);
    }

    @Test
    public void whenHasSocialOnCache_withMovieWithoutVoteCount_returnFail() {
        mMovie.setVoteCount(null);
        mMovieCacheMvpHelper.saveMovie(mMovie);

        Single<Boolean> hasMovieSocialOnCacheSingle = mMovieCacheMvpHelper.hasMovieSocialOnCache(mMovie.getId());
        TestObserver<Boolean> testObserver = hasMovieSocialOnCacheSingle.test();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValue(false);
    }

    @Test
    public void whenHasSocialOnCache_withMovieWithoutPopularity_returnFail() {
        mMovie.setPopularity(null);
        mMovieCacheMvpHelper.saveMovie(mMovie);

        Single<Boolean> hasMovieSocialOnCacheSingle = mMovieCacheMvpHelper.hasMovieSocialOnCache(mMovie.getId());
        TestObserver<Boolean> testObserver = hasMovieSocialOnCacheSingle.test();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValue(false);
    }

}
