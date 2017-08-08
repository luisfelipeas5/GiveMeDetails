package br.com.luisfelipeas5.givemedetails.model.datamangers;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.helpers.MovieApiMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieCacheMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import br.com.luisfelipeas5.givemedetails.model.model.reviews.Review;
import br.com.luisfelipeas5.givemedetails.model.model.trailer.Trailer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class MovieDataManager implements MovieMvpDataManager {

    private MovieApiMvpHelper movieApiMvpHelper;
    private MovieCacheMvpHelper movieCacheMvpHelper;

    public MovieDataManager(MovieApiMvpHelper movieApiMvpHelper, MovieCacheMvpHelper movieCacheMvpHelper) {
        this.movieApiMvpHelper = movieApiMvpHelper;
        this.movieCacheMvpHelper = movieCacheMvpHelper;
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
        return movieApiMvpHelper
                .getMovie(movieId)
                .map(new Function<Movie, Movie>() {
                    @Override
                    public Movie apply(@NonNull Movie movie) throws Exception {
                        movieCacheMvpHelper.saveMovie(movie);
                        return movie;
                    }
                })
                .singleOrError();
    }

    @Override
    public Single<String> getMoviePosterUrl(final int width, final String movieId) {
        return movieCacheMvpHelper.hasMoviePosterOnCache(movieId)
                .flatMap(getMovieCacheMapper(movieId))
                .flatMap(new Function<Movie, Single<String>>() {
                    @Override
                    public Single<String> apply(@NonNull Movie movie) throws Exception {
                        return Single.just(movie.getPoster(width));
                    }
                });
    }

    @Override
    public Single<String> getMovieTitle(String movieId) {
        return movieCacheMvpHelper.hasMovieTitleOnCache(movieId)
                .flatMap(getMovieCacheMapper(movieId))
                .flatMap(new Function<Movie, Single<String>>() {
                    @Override
                    public Single<String> apply(@NonNull Movie movie) throws Exception {
                        String movieTitle = movie.getTitle();
                        return Single.just(movieTitle);
                    }
                });
    }

    @Override
    public Single<Movie> getMovieSummary(final String movieId) {
        return movieCacheMvpHelper.hasMovieSummaryOnCache(movieId)
                .flatMap(getMovieSummaryCacheMapper(movieId));
    }

    @Override
    public Single<Movie> getMovieSocial(String movieId) {
        return movieCacheMvpHelper.hasMovieSocialOnCache(movieId)
                .flatMap(getMovieSocialCacheMapper(movieId));
    }

    @Override
    public Single<List<Trailer>> getMovieTrailers(String movieId) {
        if (movieId != null) {
            return movieApiMvpHelper.getTrailers(movieId)
                    .singleOrError();
        }
        return null;
    }

    @Override
    public Single<List<Review>> getMovieReviews(String movieId) {
        return null;
    }

    @Override
    public Single<List<Review>> getMovieReviewsByPage(String movieId, int pageIndex) {
        return null;
    }

    @android.support.annotation.NonNull
    private Function<Boolean, SingleSource<Movie>> getMovieCacheMapper(final String movieId) {
        return new Function<Boolean, SingleSource<Movie>>() {
            @Override
            public SingleSource<Movie> apply(@NonNull Boolean isMovieDetailCached) throws Exception {
                if (isMovieDetailCached) {
                    return movieCacheMvpHelper.getMovie(movieId);
                }
                return movieApiMvpHelper.getMovie(movieId).singleOrError();
            }
        };
    }

    @android.support.annotation.NonNull
    private Function<Boolean, SingleSource<Movie>> getMovieSummaryCacheMapper(final String movieId) {
        return new Function<Boolean, SingleSource<Movie>>() {
            @Override
            public SingleSource<Movie> apply(@NonNull Boolean isMovieDetailCached) throws Exception {
                if (isMovieDetailCached) {
                    return movieCacheMvpHelper.getMovie(movieId);
                }
                return movieApiMvpHelper.getMovieSummary(movieId).singleOrError();
            }
        };
    }

    @android.support.annotation.NonNull
    private Function<Boolean, SingleSource<Movie>> getMovieSocialCacheMapper(final String movieId) {
        return new Function<Boolean, SingleSource<Movie>>() {
            @Override
            public SingleSource<Movie> apply(@NonNull Boolean isMovieDetailCached) throws Exception {
                if (isMovieDetailCached) {
                    return movieCacheMvpHelper.getMovie(movieId);
                }
                return movieApiMvpHelper.getMovieSocial(movieId).singleOrError();
            }
        };
    }
}
