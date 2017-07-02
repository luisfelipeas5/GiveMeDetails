package br.com.luisfelipeas5.givemedetails.model.datamangers;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.model.Movie;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface MovieMvpDataManager {
    Single<List<Movie>> getPopularMovies();

    Single<List<Movie>> getTopRatedMovies();

    Single<Movie> getMovie(String movieId);
}
