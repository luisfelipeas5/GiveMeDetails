package br.com.luisfelipeas5.givemedetails.model.helpers;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import br.com.luisfelipeas5.givemedetails.model.model.trailer.Trailer;
import io.reactivex.Observable;

public interface MovieApiMvpHelper {
    Observable<List<Movie>> getPopular();

    Observable<List<Movie>> getTopRated();

    Observable<Movie> getMovie(String movieId);

    Observable<Movie> getMovieSummary(String movieId);

    Observable<Movie> getMovieSocial(String movieId);

    Observable<List<Trailer>> getTrailers(String movieId);
}
