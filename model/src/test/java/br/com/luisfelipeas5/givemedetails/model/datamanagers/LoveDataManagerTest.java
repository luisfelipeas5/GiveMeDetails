package br.com.luisfelipeas5.givemedetails.model.datamanagers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieDataManager;
import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.helpers.DatabaseMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieApiMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieCacheMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoveDataManagerTest {

    private static final String MOVIE_ID_MOCKED = "Movie id mocked";
    private MovieMvpDataManager mMovieMvpDataManager;

    @Mock
    private MovieApiMvpHelper mApiMvpHelper;
    @Mock
    private MovieCacheMvpHelper mCacheMvpHelper;
    @Mock
    private DatabaseMvpHelper mDatabaseMvpHelper;
    @Mock
    private Movie mMovie;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(mMovie.getId()).thenReturn(MOVIE_ID_MOCKED);

        when(mDatabaseMvpHelper.isLoved(MOVIE_ID_MOCKED)).thenReturn(Single.just(false));
        when(mDatabaseMvpHelper.setIsLoved(getMovieMatcher(), eq(true))).thenReturn(Completable.complete());
        when(mDatabaseMvpHelper.setIsLoved(getMovieMatcher(), eq(false))).thenReturn(Completable.complete());

        when(mApiMvpHelper.getMovie(MOVIE_ID_MOCKED)).thenReturn(Observable.just(mMovie));

        mMovieMvpDataManager = new MovieDataManager(mApiMvpHelper, mCacheMvpHelper, mDatabaseMvpHelper);
    }

    @Test
    public void whenToggleMovieLove_singleCompleteAndNoError_success() {
        Single<Boolean> toggleLoveSingle = mMovieMvpDataManager.toggleMovieLove(MOVIE_ID_MOCKED);
        TestObserver<Boolean> testObserver = toggleLoveSingle.test();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
    }

    @Test
    public void whenToggleMovieLove_callGetMovieOfApiHelper_success() {
        when(mDatabaseMvpHelper.isLoved(MOVIE_ID_MOCKED)).thenReturn(Single.just(false));

        Single<Boolean> toggleLoveSingle = mMovieMvpDataManager.toggleMovieLove(MOVIE_ID_MOCKED);
        toggleLoveSingle.test();
        verify(mApiMvpHelper).getMovie(MOVIE_ID_MOCKED);
    }

    @Test
    public void whenToggleMovieLove_CallGetMovieOfDatabaseHelper_success() {
        when(mDatabaseMvpHelper.isLoved(MOVIE_ID_MOCKED)).thenReturn(Single.just(true));

        Single<Boolean> toggleLoveSingle = mMovieMvpDataManager.toggleMovieLove(MOVIE_ID_MOCKED);
        toggleLoveSingle.test();
        verify(mDatabaseMvpHelper).getMovie(MOVIE_ID_MOCKED);
    }

    @Test
    public void whenToggleMovieLove_callIsLovedOfDatabaseHelper_success() {
        Single<Boolean> toggleLoveSingle = mMovieMvpDataManager.toggleMovieLove(MOVIE_ID_MOCKED);
        toggleLoveSingle.test();
        verify(mDatabaseMvpHelper).isLoved(MOVIE_ID_MOCKED);
    }

    @Test
    public void whenToggleMovieLove_callSetLoveOfDatabaseHelper_withFalse_success() {
        when(mDatabaseMvpHelper.isLoved(MOVIE_ID_MOCKED)).thenReturn(Single.just(true));

        Single<Boolean> toggleLoveSingle = mMovieMvpDataManager.toggleMovieLove(MOVIE_ID_MOCKED);
        toggleLoveSingle.test();

        verify(mDatabaseMvpHelper).setIsLoved(getMovieMatcher(), eq(false));
    }

    @Test
    public void whenToggleMovieLove_callSetLoveOfDatabaseHelper_withTrue_success() {
        when(mDatabaseMvpHelper.isLoved(MOVIE_ID_MOCKED)).thenReturn(Single.just(false));

        Single<Boolean> toggleLoveSingle = mMovieMvpDataManager.toggleMovieLove(MOVIE_ID_MOCKED);
        toggleLoveSingle.test();

        verify(mDatabaseMvpHelper).setIsLoved(getMovieMatcher(), eq(true));
    }

    @Test
    public void whenToggleMovieLove_returnTrue_success() {
        when(mDatabaseMvpHelper.isLoved(MOVIE_ID_MOCKED)).thenReturn(Single.just(false));

        Single<Boolean> toggleLoveSingle = mMovieMvpDataManager.toggleMovieLove(MOVIE_ID_MOCKED);
        TestObserver<Boolean> testObserver = toggleLoveSingle.test();
        testObserver.assertValue(true);
    }

    @Test
    public void whenToggleMovieLove_returnFalse_success() {
        when(mDatabaseMvpHelper.isLoved(MOVIE_ID_MOCKED)).thenReturn(Single.just(true));

        Single<Boolean> toggleLoveSingle = mMovieMvpDataManager.toggleMovieLove(MOVIE_ID_MOCKED);
        TestObserver<Boolean> testObserver = toggleLoveSingle.test();
        testObserver.assertValue(false);
    }

    @Test
    public void whenIsMovieLoved_callIsLovedOfDataManager_success() {
        Single<Boolean> movieLovedSingle = mMovieMvpDataManager.isMovieLoved(MOVIE_ID_MOCKED);
        movieLovedSingle.test();
        verify(mDatabaseMvpHelper).isLoved(MOVIE_ID_MOCKED);
    }

    @Test
    public void whenIsMovieLoved_assertTrue_success() {
        when(mDatabaseMvpHelper.isLoved(MOVIE_ID_MOCKED)).thenReturn(Single.just(true));

        Single<Boolean> movieLovedSingle = mMovieMvpDataManager.isMovieLoved(MOVIE_ID_MOCKED);
        TestObserver<Boolean> testObserver = movieLovedSingle.test();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValue(true);
    }

    @Test
    public void whenIsMovieLoved_assertFalse_success() {
        when(mDatabaseMvpHelper.isLoved(MOVIE_ID_MOCKED)).thenReturn(Single.just(false));

        Single<Boolean> movieLovedSingle = mMovieMvpDataManager.isMovieLoved(MOVIE_ID_MOCKED);
        TestObserver<Boolean> testObserver = movieLovedSingle.test();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValue(false);
    }

    private Movie getMovieMatcher() {
        return Matchers.argThat(new Matcher<Movie>() {
            @Override
            public boolean matches(Object item) {
                Movie movie = (Movie) item;
                return movie != null &&
                        movie.getId().equals(MOVIE_ID_MOCKED);
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
