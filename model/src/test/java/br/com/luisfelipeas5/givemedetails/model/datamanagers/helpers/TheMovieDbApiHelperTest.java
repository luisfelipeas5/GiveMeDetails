package br.com.luisfelipeas5.givemedetails.model.datamanagers.helpers;

import org.junit.Before;
import org.junit.Test;

import br.com.luisfelipeas5.givemedetails.model.helpers.MovieApiMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.TheMovieDbApiHelper;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

public class TheMovieDbApiHelperTest {

    private MovieApiMvpHelper mMovieApiMvpHelper;

    @Before
    public void setUp() {
        mMovieApiMvpHelper = new TheMovieDbApiHelper(null);
    }

    @Test
    public void whenGetMoviePosterUrl_withExactlyWidth_success() {
        int width = 185;
        String posterSuffixPath = "/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";

        Single<String> moviePosterUrlSingle = mMovieApiMvpHelper.getMoviePosterUrl(width, posterSuffixPath);
        TestObserver<String> testObserver = moviePosterUrlSingle.test();
        testObserver.assertValue("http://image.tmdb.org/t/p/w" + width + posterSuffixPath);
    }

    @Test
    public void whenGetMoviePosterUrl_withNotExactlyWidth_success() {
        int width = 200;
        String posterSuffixPath = "/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";

        Single<String> moviePosterUrlSingle = mMovieApiMvpHelper.getMoviePosterUrl(width, posterSuffixPath);
        TestObserver<String> testObserver = moviePosterUrlSingle.test();
        testObserver.assertValue("http://image.tmdb.org/t/p/w342" + posterSuffixPath);
    }

    @Test
    public void whenGetMoviePosterUrl_withSmallestWidth_success() {
        int width = 91;
        String posterSuffixPath = "/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";

        Single<String> moviePosterUrlSingle = mMovieApiMvpHelper.getMoviePosterUrl(width, posterSuffixPath);
        TestObserver<String> testObserver = moviePosterUrlSingle.test();
        testObserver.assertValue("http://image.tmdb.org/t/p/w92" + posterSuffixPath);
    }

    @Test
    public void whenGetMoviePosterUrl_withLongestWidth_success() {
        int width = 781;
        String posterSuffixPath = "/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";

        Single<String> moviePosterUrlSingle = mMovieApiMvpHelper.getMoviePosterUrl(width, posterSuffixPath);
        TestObserver<String> testObserver = moviePosterUrlSingle.test();
        testObserver.assertValue("http://image.tmdb.org/t/p/original" + posterSuffixPath);
    }

}
