package br.com.luisfelipeas5.givemedetails.model.helpers;

import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import io.reactivex.Single;

public interface MovieCacheMvpHelper {
    Single<Boolean> saveMovie(Movie movie);

    Single<Movie> getMovie(String movieId);

    Single<Boolean> hasMoviePosterOnCache(String movieId);

    Single<Boolean> hasMovieTitleOnCache(String movieId);

    Single<Boolean> hasMovieSummaryOnCache(String movieId);

    Single<Boolean> hasMovieSocialOnCache(String movieId);

    Single<Boolean> clearCache();
}
