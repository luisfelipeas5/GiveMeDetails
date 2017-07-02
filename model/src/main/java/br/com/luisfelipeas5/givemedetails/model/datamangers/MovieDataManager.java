package br.com.luisfelipeas5.givemedetails.model.datamangers;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.helpers.MovieApiMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.model.Movie;
import br.com.luisfelipeas5.givemedetails.model.model.MoviesResponseBody;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class MovieDataManager implements MovieMvpDataManager {

    private MovieApiMvpHelper movieApiMvpHelper;

    public MovieDataManager(MovieApiMvpHelper movieApiMvpHelper) {
        this.movieApiMvpHelper = movieApiMvpHelper;
    }

    @Override
    public Single<List<Movie>> getPopularMovies() {
        return movieApiMvpHelper.getPopular()
                .flatMap(getMovieResponseMapper())
                .singleOrError();
    }

    @Override
    public Single<List<Movie>> getTopRatedMovies() {
        return movieApiMvpHelper.getTopRated()
                .flatMap(getMovieResponseMapper())
                .singleOrError();
    }

    @Override
    public Single<Movie> getMovie(String movieId) {
        if (movieId == null || movieId.trim().isEmpty()) {
            return null;
        }
        return movieApiMvpHelper.getMovie(movieId).singleOrError();
    }

    @android.support.annotation.NonNull
    private Function<MoviesResponseBody, Observable<List<Movie>>> getMovieResponseMapper() {
        return new Function<MoviesResponseBody, Observable<List<Movie>>>() {
            @Override
            public Observable<List<Movie>> apply(@NonNull MoviesResponseBody moviesResponseBody) throws Exception {
                List<Movie> movies = moviesResponseBody.getMovies();
                return Observable.just(movies);
            }
        };
    }
}
