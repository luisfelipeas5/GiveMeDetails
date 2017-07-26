package br.com.luisfelipeas5.givemedetails.model.datamangers;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.model.Movie;
import io.reactivex.Single;

public interface MovieMvpDataManager {
    Single<List<Movie>> getPopularMovies();

    Single<List<Movie>> getTopRatedMovies();

    Single<Movie> getMovie(String movieId);

    Single<String> getMoviePosterUrl(int width, String movieId);

    Single<String> getMovieTitle(String movieId);

    Single<Movie> getMovieSummary(String movieId);

}
