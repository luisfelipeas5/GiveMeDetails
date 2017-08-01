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
import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieTMDb;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

@RunWith(AndroidJUnit4.class)
public class MovieCacheHelperSummaryIntegrationTest {

    private MovieCacheMvpHelper mMovieCacheMvpHelper;
    private MovieCacheDatabase mMovieCacheDatabase;
    private MovieTMDb mMovieSummaryMocked;

    @Before
    public void setUp() {
        Context applicationContext = InstrumentationRegistry.getTargetContext();

        mMovieCacheDatabase = Room
                .inMemoryDatabaseBuilder(applicationContext, MovieCacheDatabase.class)
                .allowMainThreadQueries()
                .build();
        mMovieCacheMvpHelper = new MovieCacheHelper(mMovieCacheDatabase);

        mMovieSummaryMocked = new MovieTMDb();
        mMovieSummaryMocked.setId("Movie Id Mocked" + System.currentTimeMillis());
        mMovieSummaryMocked.setTitle("Title Mocked");
        mMovieSummaryMocked.setOriginalTitle("Original Title Mocked");
        mMovieSummaryMocked.setOverview("Overview Mocked");
        mMovieSummaryMocked.setReleaseDate("2018-01-01");
        mMovieCacheMvpHelper.saveMovie(mMovieSummaryMocked);
    }

    @After
    public void closeDb() {
        mMovieCacheDatabase.close();
    }

    @Test
    public void whenHasMovieSummaryOnCache_returnTrue_success() {
        Single<Boolean> hasSummarySingle = mMovieCacheMvpHelper.hasMovieSummaryOnCache(mMovieSummaryMocked.getId());
        TestObserver<Boolean> test = hasSummarySingle.test();
        test.assertComplete();
        test.assertValue(true);
        test.assertNoErrors();
    }

    @Test
    public void whenHasMovieSummaryOnCache_movieWithoutTitle_returnFalse_success() {
        mMovieSummaryMocked.setTitle(null);
        mMovieCacheMvpHelper.saveMovie(mMovieSummaryMocked);

        Single<Boolean> hasSummarySingle = mMovieCacheMvpHelper.hasMovieSummaryOnCache(mMovieSummaryMocked.getId());
        TestObserver<Boolean> test = hasSummarySingle.test();
        test.assertComplete();
        test.assertValue(false);
        test.assertNoErrors();
    }

    @Test
    public void whenHasMovieSummaryOnCache_movieWithoutOverview_returnFalse_success() {
        mMovieSummaryMocked.setOverview(null);
        mMovieCacheMvpHelper.saveMovie(mMovieSummaryMocked);

        Single<Boolean> hasSummarySingle = mMovieCacheMvpHelper.hasMovieSummaryOnCache(mMovieSummaryMocked.getId());
        TestObserver<Boolean> test = hasSummarySingle.test();
        test.assertComplete();
        test.assertValue(false);
        test.assertNoErrors();
    }

    @Test
    public void whenHasMovieSummaryOnCache_movieWithoutOriginalTitle_returnFalse_success() {
        mMovieSummaryMocked.setOriginalTitle(null);
        mMovieCacheMvpHelper.saveMovie(mMovieSummaryMocked);

        Single<Boolean> hasSummarySingle = mMovieCacheMvpHelper.hasMovieSummaryOnCache(mMovieSummaryMocked.getId());
        TestObserver<Boolean> test = hasSummarySingle.test();
        test.assertComplete();
        test.assertValue(false);
        test.assertNoErrors();
    }

    @Test
    public void whenHasMovieSummaryOnCache_movieWithoutReleaseDate_returnFalse_success() {
        mMovieSummaryMocked.setReleaseDate(null);
        mMovieCacheMvpHelper.saveMovie(mMovieSummaryMocked);

        Single<Boolean> hasSummarySingle = mMovieCacheMvpHelper.hasMovieSummaryOnCache(mMovieSummaryMocked.getId());
        TestObserver<Boolean> test = hasSummarySingle.test();
        test.assertComplete();
        test.assertValue(false);
        test.assertNoErrors();
    }

}
