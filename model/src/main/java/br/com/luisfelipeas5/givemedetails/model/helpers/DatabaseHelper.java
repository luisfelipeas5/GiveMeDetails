package br.com.luisfelipeas5.givemedetails.model.helpers;

import java.util.LinkedList;
import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.contentproviders.MovieMvpContentProvider;
import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieLove;
import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieTMDb;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class DatabaseHelper implements DatabaseMvpHelper {

    private final MovieMvpContentProvider mMovieMvpContentProvider;

    public DatabaseHelper(MovieMvpContentProvider movieMvpContentProvider) {
        mMovieMvpContentProvider = movieMvpContentProvider;
    }

    @Override
    public Single<Boolean> isLoved(final String movieId) {
        return Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<Boolean> e) throws Exception {
                e.onSuccess(mMovieMvpContentProvider.isLoved(movieId));
            }
        }).onErrorReturnItem(false);
    }

    @Override
    public Completable setIsLoved(final Movie movie, final boolean isLoved) {
        if (movie instanceof MovieTMDb) {
            final MovieTMDb movieTMDb = (MovieTMDb) movie;
            return Single.create(new SingleOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(@NonNull SingleEmitter<Integer> e) throws Exception {
                        String movieId = movieTMDb.getId();
                        Integer movieByIdCount = mMovieMvpContentProvider.getMovieByIdCount(movieId);
                        e.onSuccess(movieByIdCount);
                    }
                }).map(new Function<Integer, String>() {
                    @Override
                    public String apply(@NonNull Integer movieCount) throws Exception {
                        if (movieCount == 0) {
                            mMovieMvpContentProvider.insert(movieTMDb);
                        }
                        return movieTMDb.getId();
                    }
                }).toCompletable()
                .concatWith(Completable.create(new CompletableOnSubscribe() {
                    @Override
                    public void subscribe(@NonNull CompletableEmitter e) throws Exception {
                        String movieId = movieTMDb.getId();

                        MovieLove movieLove = new MovieLove();
                        movieLove.setMovieId(movieId);
                        movieLove.setLoved(isLoved);

                        long insert = mMovieMvpContentProvider.insert(movieLove);
                        if (insert > 0) {
                            e.onComplete();
                        } else {
                            e.onError(new Exception("Error inserting MovieLove with movieId = " + movieId));
                        }
                    }
                }));
        } else {
            return Completable.error(new Exception("Movie must be a MovieTMDb instance"));
        }
    }

    @Override
    public Observable<List<Movie>> getLovedMovies() {
        return Observable.create(new ObservableOnSubscribe<List<Movie>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Movie>> e) throws Exception {
                List<MovieTMDb> lovedMovies = mMovieMvpContentProvider.getLoved();

                e.onNext(new LinkedList<Movie>(lovedMovies));
                e.onComplete();
            }
        });
    }
}
