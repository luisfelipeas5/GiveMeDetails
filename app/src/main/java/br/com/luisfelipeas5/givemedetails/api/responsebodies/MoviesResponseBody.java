package br.com.luisfelipeas5.givemedetails.api.responsebodies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.Movie;

public class MoviesResponseBody {
    @SerializedName("results")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }
}
