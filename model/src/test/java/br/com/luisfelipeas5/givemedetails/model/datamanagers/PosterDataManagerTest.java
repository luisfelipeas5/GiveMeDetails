package br.com.luisfelipeas5.givemedetails.model.datamanagers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieDataManager;
import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieApiMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieCacheMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.model.Movie;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PosterDataManagerTest {
    private MovieMvpDataManager mMvpDataManager;

    @Mock
    private MovieApiMvpHelper mMovieApiMvpHelper;
    @Mock
    private MovieCacheMvpHelper mMovieCacheHelper;

    @Mock
    private Movie mMovieMocked;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mMvpDataManager = new MovieDataManager(mMovieApiMvpHelper, mMovieCacheHelper);
    }

    @Test
    public void whenGetMoviePosterUrl_ByIdAndWidthReturnUrl_withoutCache_success() {
        String movieId = "someMovieIdMocked";
        String posterUrl = "Poster full path mocked";
        int width = 0;

        when(mMovieMocked.getPoster(width)).thenReturn(posterUrl);
        when(mMovieCacheHelper.hasMoviePosterOnCache(movieId)).thenReturn(Single.just(false));
        when(mMovieApiMvpHelper.getMovie(movieId)).thenReturn(Observable.just(mMovieMocked));

        Single<String> moviePosterUrl = mMvpDataManager.getMoviePosterUrl(width, movieId);
        TestObserver<String> testObserver = moviePosterUrl.test();
        testObserver.assertValue(posterUrl);
        testObserver.assertComplete();
        testObserver.assertNoErrors();

        verify(mMovieMocked).getPoster(width);
        verify(mMovieCacheHelper, never()).getMovie(movieId);
        verify(mMovieCacheHelper).hasMoviePosterOnCache(movieId);
        verify(mMovieApiMvpHelper).getMovie(movieId);

    }

    @Test
    public void whenGetMoviePosterUrl_ByIdAndWidthReturnUrl_withCache_success() {
        String movieId = "someMovieIdMocked";
        String posterUrl = "Poster full path mocked";
        int width = 0;

        when(mMovieMocked.getPoster(width)).thenReturn(posterUrl);

        when(mMovieCacheHelper.hasMoviePosterOnCache(movieId)).thenReturn(Single.just(true));
        when(mMovieCacheHelper.getMovie(movieId)).thenReturn(Single.just(mMovieMocked));

        Single<String> moviePosterUrl = mMvpDataManager.getMoviePosterUrl(width, movieId);
        TestObserver<String> testObserver = moviePosterUrl.test();
        testObserver.assertValue(posterUrl);

        verify(mMovieMocked).getPoster(width);

        verify(mMovieCacheHelper).getMovie(movieId);
        verify(mMovieCacheHelper).hasMoviePosterOnCache(movieId);

        verify(mMovieApiMvpHelper, never()).getMovie(movieId);
    }

    @Test
    public void whenGetMovieTitleById_returnTitle_withoutCache_success() {
        String movieId = "someMovieIdMocked";
        String movieTitle = "movie title mocked";
        when(mMovieMocked.getTitle()).thenReturn(movieTitle);

        when(mMovieCacheHelper.hasMovieTitleOnCache(movieId)).thenReturn(Single.just(false));

        when(mMovieApiMvpHelper.getMovie(movieId)).thenReturn(Observable.just(mMovieMocked));

        Single<String> moviePosterUrl = mMvpDataManager.getMovieTitle(movieId);
        TestObserver<String> testObserver = moviePosterUrl.test();
        testObserver.assertValue(movieTitle);
        testObserver.assertComplete();
        testObserver.assertNoErrors();

        verify(mMovieMocked).getTitle();
        verify(mMovieCacheHelper, never()).getMovie(movieId);
        verify(mMovieCacheHelper).hasMovieTitleOnCache(movieId);
        verify(mMovieApiMvpHelper).getMovie(movieId);
    }

    @Test
    public void whenGetMovieTitleById_returnTitle_withCache_success() {
        String movieId = "someMovieIdMocked";
        String movieTitle = "movie title mocked";
        when(mMovieMocked.getTitle()).thenReturn(movieTitle);

        when(mMovieCacheHelper.hasMovieTitleOnCache(movieId)).thenReturn(Single.just(true));
        when(mMovieCacheHelper.getMovie(movieId)).thenReturn(Single.just(mMovieMocked));

        Single<String> moviePosterUrl = mMvpDataManager.getMovieTitle(movieId);
        TestObserver<String> testObserver = moviePosterUrl.test();
        testObserver.assertValue(movieTitle);
        testObserver.assertComplete();
        testObserver.assertNoErrors();

        verify(mMovieMocked).getTitle();
        verify(mMovieCacheHelper).getMovie(movieId);
        verify(mMovieCacheHelper).hasMovieTitleOnCache(movieId);
        verify(mMovieApiMvpHelper, never()).getMovie(movieId);
    }

}
