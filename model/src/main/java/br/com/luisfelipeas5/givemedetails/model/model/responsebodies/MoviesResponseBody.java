package br.com.luisfelipeas5.givemedetails.model.model.responsebodies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieTMDb;

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
