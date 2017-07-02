package br.com.luisfelipeas5.givemedetails.model.helpers;

import br.com.luisfelipeas5.givemedetails.model.model.Movie;
import br.com.luisfelipeas5.givemedetails.model.model.MoviesResponseBody;
import io.reactivex.Observable;

public interface MovieApiMvpHelper {
    Observable<MoviesResponseBody> getPopular();

    Observable<MoviesResponseBody> getTopRated();

    Observable<Movie> getMovie(String movieId);
}
