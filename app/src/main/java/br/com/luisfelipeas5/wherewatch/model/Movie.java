package br.com.luisfelipeas5.wherewatch.model;

import com.google.gson.annotations.SerializedName;

public class Movie {
    @SerializedName("poster_path")
    private String poster;
    @SerializedName("title")
    private String title;

    public String getPoster() {
        return poster;
    }

    public String getTitle() {
        return title;
    }
}
