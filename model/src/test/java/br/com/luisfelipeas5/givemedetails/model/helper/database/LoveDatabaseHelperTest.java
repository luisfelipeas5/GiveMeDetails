package br.com.luisfelipeas5.givemedetails.model.helper.database;

import android.support.annotation.NonNull;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.luisfelipeas5.givemedetails.model.daos.LoveDao;
import br.com.luisfelipeas5.givemedetails.model.databases.MovieDatabase;
import br.com.luisfelipeas5.givemedetails.model.helpers.DatabaseHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.DatabaseMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieLove;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoveDatabaseHelperTest {

    private static final String MOVIE_ID_MOCKED = "Movie Id Mocked";
    private DatabaseMvpHelper mDatabaseMvpHelper;

    @Mock
    private MovieDatabase mMovieDatabase;
    @Mock
    private LoveDao mLoveDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(mMovieDatabase.getLoveDao()).thenReturn(mLoveDao);

        when(mLoveDao.insert(getMovieLoveMatcher(true))).thenReturn(1L);
        when(mLoveDao.insert(getMovieLoveMatcher(false))).thenReturn(1L);

        mDatabaseMvpHelper = new DatabaseHelper(mMovieDatabase);
    }

    @Test
    public void whenSetIsMovieLoved_withTrue_callSetIsLovedOfLoveDao_success() {
        Completable setIsLovedCompletable = mDatabaseMvpHelper.setIsLoved(MOVIE_ID_MOCKED, true);
        TestObserver<Void> testObserver = setIsLovedCompletable.test();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        verify(mLoveDao).insert(getMovieLoveMatcher(true));
    }

    @Test
    public void whenSetIsMovieLoved_withFalse_callSetIsLovedOfLoveDao_success() {
        Completable setIsLovedCompletable = mDatabaseMvpHelper.setIsLoved(MOVIE_ID_MOCKED, false);
        TestObserver<Void> testObserver = setIsLovedCompletable.test();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        verify(mLoveDao).insert(getMovieLoveMatcher(false));
    }

    @Test
    public void whenIsLoved_callIsLovedOfLoveDao_returningTrue_success() {
        when(mLoveDao.isLoved(MOVIE_ID_MOCKED)).thenReturn(true);

        Single<Boolean> isLovedSingle = mDatabaseMvpHelper.isLoved(MOVIE_ID_MOCKED);
        TestObserver<Boolean> test = isLovedSingle.test();
        test.assertComplete();
        test.assertNoErrors();
        test.assertValue(true);

        verify(mLoveDao).isLoved(MOVIE_ID_MOCKED);
    }

    @Test
    public void whenIsLoved_callIsLovedOfLoveDao_returningFalse_success() {
        when(mLoveDao.isLoved(MOVIE_ID_MOCKED)).thenReturn(false);

        Single<Boolean> isLovedSingle = mDatabaseMvpHelper.isLoved(MOVIE_ID_MOCKED);
        TestObserver<Boolean> test = isLovedSingle.test();
        test.assertComplete();
        test.assertNoErrors();
        test.assertValue(false);

        verify(mLoveDao).isLoved(MOVIE_ID_MOCKED);
    }

    @NonNull
    private MovieLove getMovieLoveMatcher(final boolean isLoved) {
        return Matchers.argThat(new Matcher<MovieLove>() {
            @Override
            public boolean matches(Object item) {
                MovieLove movieLove = (MovieLove) item;
                return  movieLove != null &&
                        movieLove.getMovieId().equals(MOVIE_ID_MOCKED) &&
                        movieLove.isLoved() == isLoved;
            }

            @Override
            public void describeMismatch(Object item, Description mismatchDescription) {

            }

            @Override
            public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {

            }

            @Override
            public void describeTo(Description description) {

            }
        });
    }

}
