package br.com.luisfelipeas5.givemedetails.model.datamanagers;

import org.junit.Before;
import org.junit.Test;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MovieListDataManagerTest {
    private MovieMvpDataManager mMvpDataManager;

    @Mock
    private MovieApiMvpHelper mMovieApiMvpHelper;
    @Mock
    private MovieCacheMvpHelper mMovieCacheHelper;

    @Mock
    private List<Movie> mPopularMoviesMocked;
    @Mock
    private List<Movie> mTopRatedMoviesMocked;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mMvpDataManager = new MovieDataManager(mMovieApiMvpHelper, mMovieCacheHelper);

        when(mMovieApiMvpHelper.getPopular()).thenReturn(Observable.just(mPopularMoviesMocked));
        when(mMovieApiMvpHelper.getTopRated()).thenReturn(Observable.just(mTopRatedMoviesMocked));
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

}
