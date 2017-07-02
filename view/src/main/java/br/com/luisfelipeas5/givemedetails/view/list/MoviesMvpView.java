package br.com.luisfelipeas5.givemedetails.view.list;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.model.Movie;

public interface MoviesMvpView {
    void onMoviesReady(List<Movie> movies);

    void onGetMoviesFailed();

    void onGettingMovies(boolean isGetting);
}
