package br.com.luisfelipeas5.givemedetails.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieDataManager;
import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieApiMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.model.Movie;
import br.com.luisfelipeas5.givemedetails.model.model.MoviesResponseBody;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MovieDataManagerTest {
    private MovieMvpDataManager mMvpDataManager;
    @Mock
    private MovieApiMvpHelper mMovieApiMvpHelper;

    //Mocked responses to use in the .assertResult()
    private List<Movie> mPopularMoviesMocked;
    private List<Movie> mTopRatedMoviesMocked;
    private Movie mMovieMocked;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mMvpDataManager = new MovieDataManager(mMovieApiMvpHelper);

        mPopularMoviesMocked = new LinkedList<>();
        mPopularMoviesMocked.add(new Movie("Mistborn: The Final Empire - Part 1"));
        mPopularMoviesMocked.add(new Movie("Spider-man: Homecoming"));
        mPopularMoviesMocked.add(new Movie("Watchmen"));
        MoviesResponseBody popularMoviesResponseBody = new MoviesResponseBody();
        popularMoviesResponseBody.setMovies(mPopularMoviesMocked);
        when(mMovieApiMvpHelper.getPopular()).thenReturn(Observable.just(popularMoviesResponseBody));

        mTopRatedMoviesMocked = new LinkedList<>();
        mTopRatedMoviesMocked.add(new Movie("The Name of the Wind"));
        mTopRatedMoviesMocked.add(new Movie("Wonder woman"));
        mTopRatedMoviesMocked.add(new Movie("Gone Girl"));
        MoviesResponseBody topRatedMoviesResponseBody = new MoviesResponseBody();
        topRatedMoviesResponseBody.setMovies(mTopRatedMoviesMocked);
        when(mMovieApiMvpHelper.getTopRated()).thenReturn(Observable.just(topRatedMoviesResponseBody));

        mMovieMocked = new Movie("Elantris: the movie");
        Observable<Movie> movieObservable = Observable.just(mMovieMocked);
        when(mMovieApiMvpHelper.getMovie(Matchers.isNotNull(String.class))).thenReturn(movieObservable);
        when(mMovieApiMvpHelper.getMovie(Matchers.matches(".{1,}"))).thenReturn(movieObservable);

        Observable<Movie> errorObservable = Observable.error(new Exception());
        when(mMovieApiMvpHelper.getMovie(Matchers.matches(" {1,}"))).thenReturn(errorObservable);
        when(mMovieApiMvpHelper.getMovie(Matchers.isNull(String.class))).thenReturn(errorObservable);
        when(mMovieApiMvpHelper.getMovie("")).thenReturn(errorObservable);
    }

    @Test
    public void whenGetPopularMovies_returnMovies_success() {
        Single<List<Movie>> moviesObservable = mMvpDataManager.getPopularMovies();

        TestObserver<List<Movie>> testObserver = moviesObservable.test();
        testObserver.assertNoErrors();
        testObserver.assertComplete();

        testObserver.assertValue(mPopularMoviesMocked);

        verify(mMovieApiMvpHelper).getPopular();
    }

    @Test
    public void whenGetPopularMovies_returnMoviesEmpty_success() {
        when(mMovieApiMvpHelper.getPopular()).thenReturn(Observable.<MoviesResponseBody>empty());

        Single<List<Movie>> moviesObservable = mMvpDataManager.getPopularMovies();

        TestObserver<List<Movie>> testObserver = moviesObservable.test();
        testObserver.assertError(Exception.class);
    }

    @Test
    public void whenGetToRatedMovies_returnMovies_success() {
        Single<List<Movie>> moviesObservable = mMvpDataManager.getTopRatedMovies();

        TestObserver<List<Movie>> testObserver = moviesObservable.test();
        testObserver.assertNoErrors();
        testObserver.assertComplete();

        testObserver.assertValue(mTopRatedMoviesMocked);
        verify(mMovieApiMvpHelper).getTopRated();
    }

    @Test
    public void whenGetTopRatedMovies_returnMoviesEmpty_success() {
        when(mMovieApiMvpHelper.getTopRated()).thenReturn(Observable.<MoviesResponseBody>empty());

        Single<List<Movie>> moviesObservable = mMvpDataManager.getTopRatedMovies();

        TestObserver<List<Movie>> testObserver = moviesObservable.test();
        testObserver.assertError(Exception.class);
    }

    @Test
    public void whenGetMovie_ByValidId_returnMovie_success() {
        String movieId = "someMovieIdMocked";
        Single<Movie> movieObservable = mMvpDataManager.getMovie(movieId);

        TestObserver<Movie> testObserver = movieObservable.test();
        testObserver.assertNoErrors();
        testObserver.assertComplete();

        testObserver.assertValue(mMovieMocked);
        verify(mMovieApiMvpHelper).getMovie(movieId);
    }

    @Test
    public void whenGetMovie_ByNullId_returnMovie_failed() {
        Single<Movie> movieObservable = mMvpDataManager.getMovie(null);
        Assert.assertNull(movieObservable);
        verify(mMovieApiMvpHelper, never()).getMovie(anyString());
        verify(mMovieApiMvpHelper, never()).getMovie(isNull(String.class));
    }

    @Test
    public void whenGetMovie_ByEmptyId_returnMovie_failed() {
        Single<Movie> movieObservable = mMvpDataManager.getMovie("");
        Assert.assertNull(movieObservable);
        verify(mMovieApiMvpHelper, never()).getMovie(anyString());
        verify(mMovieApiMvpHelper, never()).getMovie(isNull(String.class));
    }

    @Test
    public void whenGetMovie_ByManySpacesId_returnMovie_failed() {
        Single<Movie> movieObservable = mMvpDataManager.getMovie("               ");
        Assert.assertNull(movieObservable);
        verify(mMovieApiMvpHelper, never()).getMovie(anyString());
        verify(mMovieApiMvpHelper, never()).getMovie(isNull(String.class));
    }

}
