package br.com.luisfelipeas5.givemedetails.model.datamanagers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieDataManager;
import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieApiMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieCacheMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.model.Movie;
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
    @Mock
    private MovieCacheMvpHelper mMovieCacheHelper;

    @Mock
    private List<Movie> mPopularMoviesMocked;
    @Mock
    private List<Movie> mTopRatedMoviesMocked;
    @Mock
    private Movie mMovieMocked;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mMvpDataManager = new MovieDataManager(mMovieApiMvpHelper, mMovieCacheHelper);

        when(mMovieApiMvpHelper.getPopular()).thenReturn(Observable.just(mPopularMoviesMocked));
        when(mMovieApiMvpHelper.getTopRated()).thenReturn(Observable.just(mTopRatedMoviesMocked));

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
        when(mMovieApiMvpHelper.getPopular()).thenReturn(Observable.<List<Movie>>empty());

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
        when(mMovieApiMvpHelper.getTopRated()).thenReturn(Observable.<List<Movie>>empty());

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

    @Test
    public void whenGetMoviePosterUrl_ByIdAndWidthReturnUrl_withoutCache_success() {
        String movieId = "someMovieIdMocked";
        String posterSuffix = "Poster suffix mocked";
        String posterUrl = "Poster full path mocked";
        int width = 0;

        when(mMovieMocked.getPoster()).thenReturn(posterSuffix);

        when(mMovieCacheHelper.hasMoviePosterOnCache(movieId)).thenReturn(Single.just(false));

        when(mMovieApiMvpHelper.getMovie(movieId)).thenReturn(Observable.just(mMovieMocked));
        when(mMovieApiMvpHelper.getMoviePosterUrl(width, posterSuffix)).thenReturn(Single.just(posterUrl));

        Single<String> moviePosterUrl = mMvpDataManager.getMoviePosterUrl(width, movieId);
        TestObserver<String> testObserver = moviePosterUrl.test();
        testObserver.assertValue(posterUrl);
        testObserver.assertComplete();
        testObserver.assertNoErrors();

        verify(mMovieMocked).getPoster();
        verify(mMovieCacheHelper, never()).getMovie(movieId);
        verify(mMovieCacheHelper).hasMoviePosterOnCache(movieId);
        verify(mMovieApiMvpHelper).getMovie(movieId);
        verify(mMovieApiMvpHelper).getMoviePosterUrl(width, posterSuffix);

    }

    @Test
    public void whenGetMoviePosterUrl_ByIdAndWidthReturnUrl_withCache_success() {
        String movieId = "someMovieIdMocked";
        String posterSuffix = "Poster suffix mocked";
        String posterUrl = "Poster full path mocked";
        int width = 0;

        when(mMovieMocked.getPoster()).thenReturn(posterSuffix);

        when(mMovieCacheHelper.hasMoviePosterOnCache(movieId)).thenReturn(Single.just(true));
        when(mMovieCacheHelper.getMovie(movieId)).thenReturn(Single.just(mMovieMocked));

        when(mMovieApiMvpHelper.getMoviePosterUrl(width, posterSuffix)).thenReturn(Single.just(posterUrl));

        Single<String> moviePosterUrl = mMvpDataManager.getMoviePosterUrl(width, movieId);
        TestObserver<String> testObserver = moviePosterUrl.test();
        testObserver.assertValue(posterUrl);

        verify(mMovieMocked).getPoster();

        verify(mMovieCacheHelper).getMovie(movieId);
        verify(mMovieCacheHelper).hasMoviePosterOnCache(movieId);

        verify(mMovieApiMvpHelper, never()).getMovie(movieId);
        verify(mMovieApiMvpHelper).getMoviePosterUrl(width, posterSuffix);

    }

}
