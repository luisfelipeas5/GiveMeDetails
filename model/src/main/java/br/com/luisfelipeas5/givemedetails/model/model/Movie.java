package br.com.luisfelipeas5.givemedetails.model.model;

import android.os.Parcelable;

import java.util.Date;

public interface Movie extends Parcelable {
    String getPoster();

    String getPoster(int posterWidth);

    String getTitle();

    String getOverview();

    String getOriginalTitle();

    double getVoteAverage();

    long getVoteCount();

    float getPopularity();

    String getId();

    Date getReleaseDateAsDate();

    void setTitle(String title);

    void setId(String id);

}
