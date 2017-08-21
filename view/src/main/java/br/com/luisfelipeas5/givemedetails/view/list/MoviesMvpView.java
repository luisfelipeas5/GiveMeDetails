package br.com.luisfelipeas5.givemedetails.view.list;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import br.com.luisfelipeas5.givemedetails.view.MvpView;

public interface MoviesMvpView extends MvpView {
    void onMoviesReady(List<Movie> movies);

    void onGetMoviesFailed();

    void onGettingMovies(boolean isGetting);

    void onGetMovies();
}
