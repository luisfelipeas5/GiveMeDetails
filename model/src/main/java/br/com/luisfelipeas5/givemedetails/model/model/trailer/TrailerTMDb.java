package br.com.luisfelipeas5.givemedetails.model.model.trailer;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

public class TrailerTMDb implements Trailer {

    private static final String YOUTUBE_FULL_URL_FORMAT = "https://img.youtube.com/vi/%s/default.jpg";
    private static final String YOUTUBE_INTENT_URI_FORMAT = "http://www.youtube.com/watch?v=%s";
    public static final String YOUTUBE_SITE = "YouTube";

    @SerializedName("name")
    private String name;
    @SerializedName("site")
    private String site;
    @SerializedName("key")
    private String key;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getThumbUrl() {
        switch (site) {
            case YOUTUBE_SITE:
                return String.format(Locale.getDefault(), YOUTUBE_FULL_URL_FORMAT, key);
        }
        return null;
    }

    @Override
    public String getIntentUri() {
        switch (site) {
            case YOUTUBE_SITE:
                return String.format(Locale.getDefault(), YOUTUBE_INTENT_URI_FORMAT, key);
        }
        return null;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
