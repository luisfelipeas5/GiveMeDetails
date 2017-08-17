package br.com.luisfelipeas5.givemedetails.model.datamangers;

import java.util.List;
import java.util.concurrent.Callable;

import br.com.luisfelipeas5.givemedetails.model.helpers.DatabaseMvpHelper;
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

    private final MovieApiMvpHelper apiMvpHelper;
    private final MovieCacheMvpHelper cacheMvpHelper;
    private final DatabaseMvpHelper databaseMvpHelper;

    public MovieDataManager(MovieApiMvpHelper apiMvpHelper, MovieCacheMvpHelper cacheMvpHelper, DatabaseMvpHelper databaseMvpHelper) {
        this.apiMvpHelper = apiMvpHelper;
        this.cacheMvpHelper = cacheMvpHelper;
        this.databaseMvpHelper = databaseMvpHelper;
    }

    @Override
    public Single<List<Movie>> getPopularMovies() {
        return apiMvpHelper.getPopular()
                .singleOrError();
    }

    @Override
    public Single<List<Movie>> getTopRatedMovies() {
        return apiMvpHelper.getTopRated()
                .singleOrError();
    }

    @Override
    public Single<Movie> getMovie(String movieId) {
        if (movieId == null || movieId.trim().isEmpty()) {
            return null;
        }
        return apiMvpHelper
                .getMovie(movieId)
                .map(new Function<Movie, Movie>() {
                    @Override
                    public Movie apply(@NonNull Movie movie) throws Exception {
                        cacheMvpHelper.saveMovie(movie);
                        return movie;
                    }
                })
                .singleOrError();
    }

    @Override
    public Single<String> getMoviePosterUrl(final int width, final String movieId) {
        return cacheMvpHelper.hasMoviePosterOnCache(movieId)
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
        return cacheMvpHelper.hasMovieTitleOnCache(movieId)
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
        return cacheMvpHelper.hasMovieSummaryOnCache(movieId)
                .flatMap(getMovieSummaryCacheMapper(movieId));
    }

    @Override
    public Single<Movie> getMovieSocial(String movieId) {
        return cacheMvpHelper.hasMovieSocialOnCache(movieId)
                .flatMap(getMovieSocialCacheMapper(movieId));
    }

    @Override
    public Single<List<Trailer>> getMovieTrailers(String movieId) {
        if (movieId != null) {
            return apiMvpHelper.getTrailers(movieId)
                    .singleOrError();
        }
        return null;
    }

    @Override
    public Single<List<Review>> getMovieReviewsByPage(String movieId, int pageIndex) {
        return apiMvpHelper.getReviews(movieId, pageIndex).singleOrError();
    }

    @Override
    public Single<Boolean> toggleMovieLove(final String movieId) {
        return databaseMvpHelper.isLoved(movieId)
                .flatMap(new Function<Boolean, Single<
                        Boolean>>() {
                    @Override
                    public Single<Boolean> apply(@NonNull Boolean isLoved) throws Exception {
                        final boolean newLoveStatus = !isLoved;
                        return databaseMvpHelper.setIsLoved(movieId, newLoveStatus)
                                .toSingle(new Callable<Boolean>() {
                                    @Override
                                    public Boolean call() throws Exception {
                                        return newLoveStatus;
                                    }
                                });
                    }
                });
    }

    @android.support.annotation.NonNull
    private Function<Boolean, SingleSource<Movie>> getMovieCacheMapper(final String movieId) {
        return new Function<Boolean, SingleSource<Movie>>() {
            @Override
            public SingleSource<Movie> apply(@NonNull Boolean isMovieDetailCached) throws Exception {
                if (isMovieDetailCached) {
                    return cacheMvpHelper.getMovie(movieId);
                }
                return apiMvpHelper.getMovie(movieId).singleOrError();
            }
        };
    }

    @android.support.annotation.NonNull
    private Function<Boolean, SingleSource<Movie>> getMovieSummaryCacheMapper(final String movieId) {
        return new Function<Boolean, SingleSource<Movie>>() {
            @Override
            public SingleSource<Movie> apply(@NonNull Boolean isMovieDetailCached) throws Exception {
                if (isMovieDetailCached) {
                    return cacheMvpHelper.getMovie(movieId);
                }
                return apiMvpHelper.getMovieSummary(movieId).singleOrError();
            }
        };
    }

    @android.support.annotation.NonNull
    private Function<Boolean, SingleSource<Movie>> getMovieSocialCacheMapper(final String movieId) {
        return new Function<Boolean, SingleSource<Movie>>() {
            @Override
            public SingleSource<Movie> apply(@NonNull Boolean isMovieDetailCached) throws Exception {
                if (isMovieDetailCached) {
                    return cacheMvpHelper.getMovie(movieId);
                }
                return apiMvpHelper.getMovieSocial(movieId).singleOrError();
            }
        };
    }
}
