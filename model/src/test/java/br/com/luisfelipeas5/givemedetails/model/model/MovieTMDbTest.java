package br.com.luisfelipeas5.givemedetails.model.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MovieTMDbTest {

    private Movie mMovie;
    private static final String POSTER_SUFFIX_PATH = "/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";

    @Before
    public void setUp() {
        mMovie = new MovieTMDb();
        ((MovieTMDb) mMovie).setPosterSuffix(POSTER_SUFFIX_PATH);
    }

    @Test
    public void whenGetMoviePosterUrl_withExactlyWidth_success() {
        int width = 185;
        String expected = "http://image.tmdb.org/t/p/w" + width + POSTER_SUFFIX_PATH;
        Assert.assertEquals(expected, mMovie.getPoster(width));
    }

    @Test
    public void whenGetMoviePosterUrl_withNotExactlyWidth_success() {
        int width = 200;
        String expected = "http://image.tmdb.org/t/p/w342" + POSTER_SUFFIX_PATH;
        Assert.assertEquals(expected, mMovie.getPoster(width));
    }

    @Test
    public void whenGetMoviePosterUrl_withSmallestWidth_success() {
        int width = 91;
        String expected = "http://image.tmdb.org/t/p/w92" + POSTER_SUFFIX_PATH;
        Assert.assertEquals(expected, mMovie.getPoster(width));
    }

    @Test
    public void whenGetMoviePosterUrl_withLongestWidth_success() {
        int width = 781;
        String expected = "http://image.tmdb.org/t/p/original" + POSTER_SUFFIX_PATH;
        Assert.assertEquals(expected, mMovie.getPoster(width));
    }

    @Test
    public void whenGetMoviePosterUrl_getOriginal_success() {
        String expected = "http://image.tmdb.org/t/p/original" + POSTER_SUFFIX_PATH;
        Assert.assertEquals(expected, mMovie.getPoster());
    }

}
