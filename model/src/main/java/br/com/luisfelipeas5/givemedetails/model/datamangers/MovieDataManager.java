package br.com.luisfelipeas5.givemedetails.model.datamangers;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.helpers.MovieApiMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.model.Movie;
import io.reactivex.Single;

public class MovieDataManager implements MovieMvpDataManager {

    private MovieApiMvpHelper movieApiMvpHelper;

    public MovieDataManager(MovieApiMvpHelper movieApiMvpHelper) {
        this.movieApiMvpHelper = movieApiMvpHelper;
    }

    @Override
    public Single<List<Movie>> getPopularMovies() {
        return movieApiMvpHelper.getPopular()
                .singleOrError();
    }

    @Override
    public Single<List<Movie>> getTopRatedMovies() {
        return movieApiMvpHelper.getTopRated()
                .singleOrError();
    }

    @Override
    public Single<Movie> getMovie(String movieId) {
        if (movieId == null || movieId.trim().isEmpty()) {
            return null;
        }
        return movieApiMvpHelper.getMovie(movieId).singleOrError();
    }
}
