package br.com.luisfelipeas5.givemedetails.model.helpers;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface DatabaseMvpHelper {
    Single<Boolean> isLoved(String movieId);

    Completable setIsLoved(Movie movie, boolean isLoved);

    Observable<List<Movie>> getLovedMovies();

    Single<Movie> getMovie(String movieId);
}
