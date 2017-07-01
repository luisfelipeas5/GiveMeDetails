package br.com.luisfelipeas5.givemedetails.model.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesResponseBody {
    @SerializedName("results")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
