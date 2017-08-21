package br.com.luisfelipeas5.givemedetails.model.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.luisfelipeas5.givemedetails.model.model.trailer.TrailerTMDb;

public class TrailerTMDbTest {

    private TrailerTMDb mTrailer;

    @Before
    public void setUp() {
        mTrailer = new TrailerTMDb();
    }

    @Test
    public void whenGetThumb_withYoutubeSite_returnYoutubeUrl_success() {
        String movieKey = "rk-dF1lIbIg";
        mTrailer.setSite(TrailerTMDb.YOUTUBE_SITE);
        mTrailer.setKey(movieKey);

        Assert.assertEquals("https://img.youtube.com/vi/" + movieKey + "/default.jpg", mTrailer.getThumbUrl());
    }

    @Test
    public void whenGetIntentUri_withYoutubeSite_returnYoutubeIntent_success() {
        String movieKey = "rk-dF1lIbIg";
        mTrailer.setSite(TrailerTMDb.YOUTUBE_SITE);
        mTrailer.setKey(movieKey);

        Assert.assertEquals("http://www.youtube.com/watch?v=" + movieKey, mTrailer.getIntentUri());
    }

}
