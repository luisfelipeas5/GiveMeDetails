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

    private final MovieApiMvpHelper mApiMvpHelper;
    private final MovieCacheMvpHelper cacheMvpHelper;
    private final DatabaseMvpHelper mDatabaseMvpHelper;

    public MovieDataManager(MovieApiMvpHelper apiMvpHelper, MovieCacheMvpHelper cacheMvpHelper, DatabaseMvpHelper databaseMvpHelper) {
        this.mApiMvpHelper = apiMvpHelper;
        this.cacheMvpHelper = cacheMvpHelper;
        this.mDatabaseMvpHelper = databaseMvpHelper;
    }

    @Override
    public Single<List<Movie>> getPopularMovies() {
        return mApiMvpHelper.getPopular()
                .singleOrError();
    }

    @Override
    public Single<List<Movie>> getTopRatedMovies() {
        return mApiMvpHelper.getTopRated()
                .singleOrError();
    }

    @Override
    public Single<List<Movie>> getLovedMovies() {
        return mDatabaseMvpHelper.getLovedMovies()
                .singleOrError();
    }

    @Override
    public Single<Movie> getMovie(final String movieId) {
        if (movieId == null || movieId.trim().isEmpty()) {
            return null;
        }
        return mDatabaseMvpHelper.isLoved(movieId)
                .flatMap(new Function<Boolean, Single<Movie>>() {
                    @Override
                    public Single<Movie> apply(@NonNull Boolean isLoved) throws Exception {
                        if (isLoved) {
                            return mDatabaseMvpHelper.getMovie(movieId);
                        } else {
                            return mApiMvpHelper.getMovie(movieId).singleOrError();
                        }
                    }
                })
                .flatMap(new Function<Movie, Single<Movie>>() {
                    @Override
                    public Single<Movie> apply(@NonNull final Movie movie) throws Exception {
                        return cacheMvpHelper.clearCache()
                                .map(new Function<Boolean, Movie>() {
                                    @Override
                                    public Movie apply(@NonNull Boolean aBoolean) throws Exception {
                                        return movie;
                                    }
                                });
                    }
                })
                .flatMap(new Function<Movie, Single<Movie>>() {
                    @Override
                    public Single<Movie> apply(@NonNull final Movie movie) throws Exception {
                        return cacheMvpHelper.saveMovie(movie)
                                .map(new Function<Boolean, Movie>() {
                                    @Override
                                    public Movie apply(@NonNull Boolean aBoolean) throws Exception {
                                        return movie;
                                    }
                                });
                    }
                });
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
            return mApiMvpHelper.getTrailers(movieId)
                    .singleOrError();
        }
        return null;
    }

    @Override
    public Single<List<Review>> getMovieReviewsByPage(String movieId, int pageIndex) {
        return mApiMvpHelper.getReviews(movieId, pageIndex).singleOrError();
    }

    @Override
    public Single<Boolean> toggleMovieLove(final String movieId) {
        return isMovieLoved(movieId)
                .flatMap(new Function<Boolean, Single<Boolean>>() {
                    @Override
                    public Single<Boolean> apply(@NonNull Boolean isLoved) throws Exception {
                        final boolean newLoveStatus = !isLoved;
                        Single<Movie> movieSingle;
                        if (newLoveStatus) {
                            movieSingle = mApiMvpHelper.getMovie(movieId).singleOrError();
                        } else {
                            movieSingle = mDatabaseMvpHelper.getMovie(movieId);
                        }

                        return movieSingle
                                .flatMap(new Function<Movie, Single<Boolean>>() {
                                    @Override
                                    public Single<Boolean> apply(@NonNull Movie movie) throws Exception {
                                        return mDatabaseMvpHelper.setIsLoved(movie, newLoveStatus)
                                                .toSingle(new Callable<Boolean>() {
                                                    @Override
                                                    public Boolean call() throws Exception {
                                                        return newLoveStatus;
                                                    }
                                                });
                                    }
                                });
                    }
                });
    }

    @Override
    public Single<Boolean> isMovieLoved(String movieId) {
        return mDatabaseMvpHelper.isLoved(movieId);
    }

    @android.support.annotation.NonNull
    private Function<Boolean, SingleSource<Movie>> getMovieCacheMapper(final String movieId) {
        return new Function<Boolean, SingleSource<Movie>>() {
            @Override
            public SingleSource<Movie> apply(@NonNull Boolean isMovieDetailCached) throws Exception {
                if (isMovieDetailCached) {
                    return cacheMvpHelper.getMovie(movieId);
                }
                return mApiMvpHelper.getMovie(movieId).singleOrError();
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
                return mApiMvpHelper.getMovieSummary(movieId).singleOrError();
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
                return mApiMvpHelper.getMovieSocial(movieId).singleOrError();
            }
        };
    }
}
