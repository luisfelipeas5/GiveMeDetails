package br.com.luisfelipeas5.givemedetails.model.helpers.database;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.luisfelipeas5.givemedetails.model.helpers.DatabaseHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.DatabaseMvpHelper;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

@RunWith(AndroidJUnit4.class)
public class DatabaseHelperLoveIntegrationTest {

    private static final String MOVIE_ID_MOCKED = "Movie Id mocked";
    private DatabaseMvpHelper mDatabaseMvpHelper;

    @Before
    public void setUp() {
        mDatabaseMvpHelper = new DatabaseHelper();
    }

    @Test
    public void whenIsLoved_returnFalse_success() {
        Single<Boolean> isLovedSingle = mDatabaseMvpHelper.isLoved(MOVIE_ID_MOCKED);
        TestObserver<Boolean> testObserver = isLovedSingle.test();
        testObserver.assertValue(false);
        testObserver.assertComplete();
        testObserver.assertNoErrors();
    }

    @Test
    public void whenIsLoved_returnTrue_success() {
        mDatabaseMvpHelper.setIsLoved(MOVIE_ID_MOCKED, true);

        Single<Boolean> isLovedSingle = mDatabaseMvpHelper.isLoved(MOVIE_ID_MOCKED);
        TestObserver<Boolean> testObserver = isLovedSingle.test();
        testObserver.assertValue(true);
        testObserver.assertComplete();
        testObserver.assertNoErrors();
    }

    @Test
    public void whenSetIsLoved_toTrue_returnComplete_success() {
        Completable setIsLovedCompletable =
                mDatabaseMvpHelper.setIsLoved(MOVIE_ID_MOCKED, true);
        TestObserver<Void> testObserver = setIsLovedCompletable.test();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
    }

    @Test
    public void whenSetIsLoved_toFalse_returnComplete_success() {
        Completable setIsLovedCompletable =
                mDatabaseMvpHelper.setIsLoved(MOVIE_ID_MOCKED, false);
        TestObserver<Void> testObserver = setIsLovedCompletable.test();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
    }

}
