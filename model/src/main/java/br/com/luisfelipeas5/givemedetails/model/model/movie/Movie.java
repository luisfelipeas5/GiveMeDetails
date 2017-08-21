package br.com.luisfelipeas5.givemedetails.model.model.movie;

import java.util.Date;

public interface Movie {
    String getPoster();

    String getPoster(int posterWidth);

    String getTitle();

    String getOverview();

    String getOriginalTitle();

    Double getVoteAverage();

    Long getVoteCount();

    Float getPopularity();

    String getId();

    Date getReleaseDateAsDate();

    void setTitle(String title);

    void setId(String id);

}
