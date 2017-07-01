package br.com.luisfelipeas5.givemedetails.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieDataManager;
import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.helpers.TheMovieDBMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.model.Movie;
import br.com.luisfelipeas5.givemedetails.model.model.MoviesResponseBody;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MovieDataManagerTest {
    private MovieMvpDataManager mMvpDataManager;
    @Mock
    private TheMovieDBMvpHelper mTheMovieDBApiHelper;
    private List<Movie> mMoviesMocked;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mMvpDataManager = new MovieDataManager(mTheMovieDBApiHelper);

        mMoviesMocked = new LinkedList<>();
        mMoviesMocked.add(new Movie("Mistborn: The Final Empire - Part 1"));

        MoviesResponseBody moviesResponseBody = new MoviesResponseBody();
        moviesResponseBody.setMovies(mMoviesMocked);
        when(mTheMovieDBApiHelper.getPopular()).thenReturn(Observable.just(moviesResponseBody));
    }

    @Test
    public void whenGetPopularMovies_returnMovies_success() {
        Observable<List<Movie>> moviesObservable = mMvpDataManager.getPopularMovies();

        TestObserver<List<Movie>> testObserver = moviesObservable.test();
        testObserver.assertNoErrors();
        testObserver.assertComplete();

        testObserver.assertValue(mMoviesMocked);

        verify(mTheMovieDBApiHelper).getPopular();
    }

}
