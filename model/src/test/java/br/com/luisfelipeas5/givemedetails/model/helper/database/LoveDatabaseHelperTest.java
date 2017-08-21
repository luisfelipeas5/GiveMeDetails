package br.com.luisfelipeas5.givemedetails.model.helper.database;

import android.support.annotation.NonNull;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.contentproviders.MovieMvpContentProvider;
import br.com.luisfelipeas5.givemedetails.model.helpers.DatabaseHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.DatabaseMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieLove;
import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieTMDb;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoveDatabaseHelperTest {

    private static final String MOVIE_ID_MOCKED = "Movie Id Mocked";
    private DatabaseMvpHelper mDatabaseMvpHelper;

    @Mock
    private MovieMvpContentProvider mMovieMvpContentProvider;
    @Mock
    private MovieTMDb mMovieTMDb;

    private List<MovieTMDb> mLovedMovies;
    @Mock
    private MovieTMDb movie0;
    @Mock
    private MovieTMDb movie1;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mLovedMovies = new LinkedList<>();
        mLovedMovies.add(movie0);
        mLovedMovies.add(movie1);

        when(mMovieTMDb.getId()).thenReturn(MOVIE_ID_MOCKED);

        when(mMovieMvpContentProvider.insert(getMovieLoveMatcher(true))).thenReturn(1L);
        when(mMovieMvpContentProvider.insert(getMovieLoveMatcher(false))).thenReturn(1L);
        when(mMovieMvpContentProvider.getLoved()).thenReturn(mLovedMovies);

        when(mMovieMvpContentProvider.insert(getMovieMatcher(MOVIE_ID_MOCKED))).thenReturn(1L);
        when(mMovieMvpContentProvider.getMovieByIdCount(MOVIE_ID_MOCKED)).thenReturn(0);

        mDatabaseMvpHelper = new DatabaseHelper(mMovieMvpContentProvider);
    }

    @Test
    public void whenSetIsMovieLoved_callInsertOfContentProvider_success() {
        Completable setIsLovedCompletable = mDatabaseMvpHelper.setIsLoved(mMovieTMDb, true);
        setIsLovedCompletable.test();
        verify(mMovieMvpContentProvider).insert(getMovieMatcher(MOVIE_ID_MOCKED));
    }

    @Test
    public void whenSetIsMovieLoved_notCallInsertOfContentProvider_success() {
        when(mMovieMvpContentProvider.getMovieByIdCount(MOVIE_ID_MOCKED)).thenReturn(1);

        Completable setIsLovedCompletable = mDatabaseMvpHelper.setIsLoved(mMovieTMDb, true);
        setIsLovedCompletable.test();
        verify(mMovieMvpContentProvider, never()).insert(getMovieMatcher(MOVIE_ID_MOCKED));
    }

    @Test
    public void whenSetIsMovieLoved_withTrue_callSetIsLovedOfContentProvider_success() {
        Completable setIsLovedCompletable = mDatabaseMvpHelper.setIsLoved(mMovieTMDb, true);
        TestObserver<Void> testObserver = setIsLovedCompletable.test();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        verify(mMovieMvpContentProvider).insert(getMovieLoveMatcher(true));
    }

    @Test
    public void whenSetIsMovieLoved_withFalse_callSetIsLovedOfContentProvider_success() {
        Completable setIsLovedCompletable = mDatabaseMvpHelper.setIsLoved(mMovieTMDb, false);
        TestObserver<Void> testObserver = setIsLovedCompletable.test();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        verify(mMovieMvpContentProvider).insert(getMovieLoveMatcher(false));
    }

    @Test
    public void whenIsLoved_callIsLovedOfContentProvider_returningTrue_success() {
        when(mMovieMvpContentProvider.isLoved(MOVIE_ID_MOCKED)).thenReturn(true);

        Single<Boolean> isLovedSingle = mDatabaseMvpHelper.isLoved(MOVIE_ID_MOCKED);
        TestObserver<Boolean> test = isLovedSingle.test();
        test.assertComplete();
        test.assertNoErrors();
        test.assertValue(true);

        verify(mMovieMvpContentProvider).isLoved(MOVIE_ID_MOCKED);
    }

    @Test
    public void whenIsLoved_callIsLovedOfContentProvider_returningFalse_success() {
        when(mMovieMvpContentProvider.isLoved(MOVIE_ID_MOCKED)).thenReturn(false);

        Single<Boolean> isLovedSingle = mDatabaseMvpHelper.isLoved(MOVIE_ID_MOCKED);
        TestObserver<Boolean> test = isLovedSingle.test();
        test.assertComplete();
        test.assertNoErrors();
        test.assertValue(false);

        verify(mMovieMvpContentProvider).isLoved(MOVIE_ID_MOCKED);
    }

    @Test
    public void whenGetLoved_callGetLovedOfContentProvider_success() {
        Observable<List<Movie>> lovedMoviesObservable = mDatabaseMvpHelper.getLovedMovies();
        TestObserver<List<Movie>> testObserver = lovedMoviesObservable.test();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValue(new LinkedList<Movie>(mLovedMovies));

        verify(mMovieMvpContentProvider).getLoved();
    }

    @Test
    public void whenGetMovie_callGetMovieOfContentProvider_success() {
        when(mMovieMvpContentProvider.getMovieById(MOVIE_ID_MOCKED)).thenReturn(mMovieTMDb);

        Single<Movie> movieSingle = mDatabaseMvpHelper.getMovie(MOVIE_ID_MOCKED);
        TestObserver<Movie> testObserver = movieSingle.test();
        testObserver.assertComplete();
        testObserver.assertValue(mMovieTMDb);
        testObserver.assertNoErrors();

        verify(mMovieMvpContentProvider).getMovieById(MOVIE_ID_MOCKED);
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

    private MovieTMDb getMovieMatcher(final String movieId) {
        return Matchers.argThat(new Matcher<MovieTMDb>() {
            @Override
            public boolean matches(Object item) {
                MovieTMDb movieTMDb = (MovieTMDb) item;
                return movieTMDb != null &&
                        movieTMDb.getId().equals(movieId);
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
