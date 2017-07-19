package br.com.luisfelipeas5.givemedetails.model.helpers;

import java.util.Date;

import br.com.luisfelipeas5.givemedetails.model.daos.MovieDao;
import br.com.luisfelipeas5.givemedetails.model.databases.MovieCacheDatabase;
import br.com.luisfelipeas5.givemedetails.model.model.Movie;
import br.com.luisfelipeas5.givemedetails.model.model.MovieTMDb;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class MovieCacheHelper implements MovieCacheMvpHelper {

    private MovieCacheDatabase mMovieCacheDatabase;

    public MovieCacheHelper(MovieCacheDatabase movieCacheDatabase) {
        mMovieCacheDatabase = movieCacheDatabase;
    }

    private Boolean isDataValid(String string) {
        return string != null && !string.isEmpty();
    }

    private Boolean isDataValid(Date date) {
        return date != null;
    }

    @Override
    public Single<Movie> getMovie(String movieId) {
        MovieDao movieDao = mMovieCacheDatabase.getMovieDao();
        return movieDao.getMovieById(movieId).singleOrError().cast(Movie.class);
    }

    @Override
    public Single<Boolean> hasMoviePosterOnCache(String movieId) {
        return getMovie(movieId)
                .map(new Function<Movie, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull Movie movie) throws Exception {
                        return isDataValid(movie.getPoster());
                    }
                });
    }

    @Override
    public Single<Boolean> hasMovieTitleOnCache(String movieId) {
        return getMovie(movieId)
                .map(new Function<Movie, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull Movie movie) throws Exception {
                        return isDataValid(movie.getTitle());
                    }
                });
    }

    @Override
    public Single<Movie> getMovieSummary(final String movieId) {
        return getMovie(movieId);
    }

    @Override
    public Single<Boolean> hasMovieSummaryOnCache(final String movieId) {
        return hasMovieOnCache(movieId)
                .flatMap(new Function<Boolean, Single<Boolean>>() {
                    @Override
                    public Single<Boolean> apply(@NonNull Boolean hasMovieOnCache) throws Exception {
                        if (hasMovieOnCache) {
                            return getMovie(movieId)
                                    .map(new Function<Movie, Boolean>() {
                                        @Override
                                        public Boolean apply(@NonNull Movie movie) throws Exception {
                                            return hasMovieSummaryData(movie);
                                        }
                                    });
                        }
                        return Single.just(false);
                    }
                });
    }

    @Override
    public void saveMovie(Movie movie) {
        MovieDao movieDao = mMovieCacheDatabase.getMovieDao();
        if (movie instanceof MovieTMDb) {
            movieDao.insert((MovieTMDb) movie);
        }
    }

    private boolean hasMovieSummaryData(@NonNull Movie movie) {
        return isDataValid(movie.getOverview()) &&
                isDataValid(movie.getReleaseDateAsDate()) &&
                isDataValid(movie.getOriginalTitle()) &&
                isDataValid(movie.getTitle());
    }

    private Single<Boolean> hasMovieOnCache(String movieId) {
        MovieDao movieDao = mMovieCacheDatabase.getMovieDao();
        Flowable<Integer> movieByIdCount = movieDao.getMovieByIdCount(movieId);
        return movieByIdCount
                .single(0)
                .map(new Function<Integer, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull Integer count) throws Exception {
                        return count > 0;
                    }
                });
    }
}
