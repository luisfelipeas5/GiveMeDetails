package br.com.luisfelipeas5.givemedetails.model.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesResponseBody {
    @SerializedName("results")
    private List<MovieTMDb> movies;

    public List<MovieTMDb> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieTMDb> movies) {
        this.movies = movies;
    }
}
