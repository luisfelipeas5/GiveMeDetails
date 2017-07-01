package br.com.luisfelipeas5.givemedetails.model.datamangers;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.helpers.TheMovieDBMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.model.Movie;
import br.com.luisfelipeas5.givemedetails.model.model.MoviesResponseBody;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class MovieDataManager implements MovieMvpDataManager {

    private TheMovieDBMvpHelper theMovieDBApiHelper;

    public MovieDataManager(TheMovieDBMvpHelper theMovieDBApiHelper) {
        this.theMovieDBApiHelper = theMovieDBApiHelper;
    }

    @Override
    public Observable<List<Movie>> getPopularMovies() {
        return theMovieDBApiHelper.getPopular()
                .flatMap(new Function<MoviesResponseBody, Observable<List<Movie>>>() {
                    @Override
                    public Observable<List<Movie>> apply(@NonNull MoviesResponseBody moviesResponseBody) throws Exception {
                        List<Movie> movies = moviesResponseBody.getMovies();
                        return Observable.just(movies);
                    }
                });
    }
}
