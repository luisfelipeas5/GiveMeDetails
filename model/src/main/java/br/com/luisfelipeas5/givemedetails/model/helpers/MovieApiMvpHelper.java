package br.com.luisfelipeas5.givemedetails.model.helpers;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.model.Movie;
import io.reactivex.Observable;

public interface MovieApiMvpHelper {
    Observable<List<Movie>> getPopular();

    Observable<List<Movie>> getTopRated();

    Observable<Movie> getMovie(String movieId);
}
