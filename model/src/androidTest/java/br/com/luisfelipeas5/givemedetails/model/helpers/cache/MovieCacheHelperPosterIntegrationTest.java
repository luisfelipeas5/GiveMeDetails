package br.com.luisfelipeas5.givemedetails.model.helpers.cache;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.luisfelipeas5.givemedetails.model.databases.MovieCacheDatabase;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieCacheHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieCacheMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.model.MovieTMDb;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

@RunWith(AndroidJUnit4.class)
public class MovieCacheHelperPosterIntegrationTest {

    private MovieCacheMvpHelper mMovieCacheMvpHelper;
    private MovieCacheDatabase mMovieCacheDatabase;

    @Before
    public void setUp() {
        Context applicationContext = InstrumentationRegistry.getTargetContext();

        mMovieCacheDatabase = Room
                .inMemoryDatabaseBuilder(applicationContext, MovieCacheDatabase.class)
                .allowMainThreadQueries()
                .build();
        mMovieCacheMvpHelper = new MovieCacheHelper(mMovieCacheDatabase);
    }

    @After
    public void closeDb() {
        mMovieCacheDatabase.close();
    }

    @Test
    public void whenHasMoviePosterOnCache_returnFalse_success() {
        MovieTMDb movieMocked = new MovieTMDb();
        movieMocked.setId("Movie Id Mocked" + System.currentTimeMillis());
        movieMocked.setPosterSuffix(null);
        mMovieCacheMvpHelper.saveMovie(movieMocked);

        Single<Boolean> hasPosterSingle = mMovieCacheMvpHelper.hasMoviePosterOnCache(movieMocked.getId());
        TestObserver<Boolean> test = hasPosterSingle.test();
        test.assertComplete();
        test.assertValue(false);
        test.assertNoErrors();
    }

    @Test
    public void whenHasMoviePosterOnCache_returnTrue_success() {
        MovieTMDb movieMocked = new MovieTMDb();
        movieMocked.setId("Movie Id Mocked" + System.currentTimeMillis());
        movieMocked.setPosterSuffix("posterSuffixMocked");
        mMovieCacheMvpHelper.saveMovie(movieMocked);

        Single<Boolean> hasPosterSingle = mMovieCacheMvpHelper.hasMoviePosterOnCache(movieMocked.getId());
        TestObserver<Boolean> test = hasPosterSingle.test();
        test.assertComplete();
        test.assertValue(true);
        test.assertNoErrors();
    }

    @Test
    public void whenHasMovieTitleOnCache_returnFalse_success() {
        MovieTMDb movieMocked = new MovieTMDb();
        movieMocked.setId("Movie Id Mocked" + System.currentTimeMillis());
        movieMocked.setTitle(null);
        mMovieCacheMvpHelper.saveMovie(movieMocked);

        Single<Boolean> hasTitleSingle = mMovieCacheMvpHelper.hasMovieTitleOnCache(movieMocked.getId());
        TestObserver<Boolean> test = hasTitleSingle.test();
        test.assertComplete();
        test.assertValue(false);
        test.assertNoErrors();
    }

    @Test
    public void whenHasMovieTitleOnCache_returnTrue_success() {
        MovieTMDb movieMocked = new MovieTMDb();
        movieMocked.setId("Movie Id Mocked" + System.currentTimeMillis());
        movieMocked.setTitle("Title Mocked");
        mMovieCacheMvpHelper.saveMovie(movieMocked);

        Single<Boolean> hasTitleSingle = mMovieCacheMvpHelper.hasMovieTitleOnCache(movieMocked.getId());
        TestObserver<Boolean> test = hasTitleSingle.test();
        test.assertComplete();
        test.assertValue(true);
        test.assertNoErrors();
    }

}
