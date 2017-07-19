package br.com.luisfelipeas5.givemedetails.model.helpers;

import br.com.luisfelipeas5.givemedetails.model.model.Movie;
import io.reactivex.Single;

public interface MovieCacheMvpHelper {
    Single<Movie> getMovie(String movieId);

    Single<Boolean> hasMoviePosterOnCache(String movieId);

    Single<Boolean> hasMovieTitleOnCache(String movieId);

    Single<Movie> getMovieSummary(String movieId);

    Single<Boolean> hasMovieSummaryOnCache(String movieId);

    void saveMovie(Movie movie);
}
