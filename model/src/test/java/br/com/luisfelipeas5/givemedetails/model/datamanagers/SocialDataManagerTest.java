package br.com.luisfelipeas5.givemedetails.model.datamanagers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieDataManager;
import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieApiMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieCacheHelper;
import br.com.luisfelipeas5.givemedetails.model.model.Movie;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SocialDataManagerTest {

    private MovieMvpDataManager movieMvpDataManager;

    @Mock
    public Movie movie;
    @Mock
    public MovieApiMvpHelper mMovieApiMvpHelper;
    @Mock
    public MovieCacheHelper mMovieCacheHelper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(movie.getId()).thenReturn("Movie Id Mocked");
        movieMvpDataManager = new MovieDataManager(mMovieApiMvpHelper, mMovieCacheHelper);
    }

    @Test
    public void whenGetSocialMovie_returnMovieFromApiHelper_success() {
        String movieId = movie.getId();
        when(mMovieApiMvpHelper.getMovieSocial(movieId))
                .thenReturn(Observable.just(movie));
        when(mMovieCacheHelper.hasMovieSocialOnCache(movieId))
                .thenReturn(Single.just(false));

        Single<Movie> movieSocialSingle = movieMvpDataManager.getMovieSocial(movieId);
        TestObserver<Movie> testObserver = movieSocialSingle.test();
        testObserver.assertValue(movie);
        testObserver.assertComplete();
        testObserver.assertNoErrors();

        verify(mMovieApiMvpHelper).getMovieSocial(movieId);
        verify(mMovieCacheHelper).hasMovieSocialOnCache(movieId);
        verify(mMovieCacheHelper, never()).getMovie(movieId);
    }

    @Test
    public void whenGetSocialMovie_returnMovieFromCacheHelper_success() {
        String movieId = movie.getId();
        when(mMovieCacheHelper.getMovie(movieId))
                .thenReturn(Single.just(movie));
        when(mMovieCacheHelper.hasMovieSocialOnCache(movieId))
                .thenReturn(Single.just(true));

        Single<Movie> movieSocialSingle = movieMvpDataManager.getMovieSocial(movieId);
        TestObserver<Movie> testObserver = movieSocialSingle.test();
        testObserver.assertValue(movie);
        testObserver.assertComplete();
        testObserver.assertNoErrors();

        verify(mMovieApiMvpHelper, never()).getMovieSocial(movieId);
        verify(mMovieCacheHelper).hasMovieSocialOnCache(movieId);
        verify(mMovieCacheHelper).getMovie(movieId);
    }

}
