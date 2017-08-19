package br.com.luisfelipeas5.givemedetails.model.datamanagers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieDataManager;
import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.helpers.DatabaseMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieApiMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieCacheMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ListDataManagerTest {
    private MovieMvpDataManager mDataManager;

    @Mock
    private MovieApiMvpHelper mMovieApiMvpHelper;
    @Mock
    private MovieCacheMvpHelper mMovieCacheHelper;
    @Mock
    private DatabaseMvpHelper mDatabaseMvpHelper;

    @Mock
    private List<Movie> mPopularMoviesMocked;
    @Mock
    private List<Movie> mTopRatedMoviesMocked;
    @Mock
    private List<Movie> mLovedMovies;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mDataManager = new MovieDataManager(mMovieApiMvpHelper, mMovieCacheHelper, mDatabaseMvpHelper);

        when(mMovieApiMvpHelper.getPopular()).thenReturn(Observable.just(mPopularMoviesMocked));
        when(mMovieApiMvpHelper.getTopRated()).thenReturn(Observable.just(mTopRatedMoviesMocked));
        when(mDatabaseMvpHelper.getLovedMovies()).thenReturn(Observable.just(mLovedMovies));
    }

    @Test
    public void whenGetPopularMovies_returnMovies_success() {
        Single<List<Movie>> moviesObservable = mDataManager.getPopularMovies();

        TestObserver<List<Movie>> testObserver = moviesObservable.test();
        testObserver.assertNoErrors();
        testObserver.assertComplete();

        testObserver.assertValue(mPopularMoviesMocked);

        verify(mMovieApiMvpHelper).getPopular();
    }

    @Test
    public void whenGetToRatedMovies_returnMovies_success() {
        Single<List<Movie>> moviesObservable = mDataManager.getTopRatedMovies();

        TestObserver<List<Movie>> testObserver = moviesObservable.test();
        testObserver.assertNoErrors();
        testObserver.assertComplete();

        testObserver.assertValue(mTopRatedMoviesMocked);
        verify(mMovieApiMvpHelper).getTopRated();
    }

    @Test
    public void whenGetLovedMovies_callGetLovedMoviesOfDatabaseHelper_success() {
        Single<List<Movie>> lovedMovies = mDataManager.getLovedMovies();
        TestObserver<List<Movie>> testObserver = lovedMovies.test();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValue(mLovedMovies);

        verify(mDatabaseMvpHelper).getLovedMovies();
    }

}
