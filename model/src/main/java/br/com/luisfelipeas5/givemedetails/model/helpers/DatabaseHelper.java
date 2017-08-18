package br.com.luisfelipeas5.givemedetails.model.helpers;

import br.com.luisfelipeas5.givemedetails.model.daos.LoveDao;
import br.com.luisfelipeas5.givemedetails.model.databases.MovieDatabase;
import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieLove;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.annotations.NonNull;

public class DatabaseHelper implements DatabaseMvpHelper {

    private final MovieDatabase mMovieDatabase;

    public DatabaseHelper(MovieDatabase movieDatabase) {
        mMovieDatabase = movieDatabase;
    }

    @Override
    public Single<Boolean> isLoved(final String movieId) {
        return Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<Boolean> e) throws Exception {
                LoveDao loveDao = mMovieDatabase.getLoveDao();
                e.onSuccess(loveDao.isLoved(movieId));
            }
        }).onErrorReturnItem(false);
    }

    @Override
    public Completable setIsLoved(final String movieId, final boolean isLoved) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(@NonNull CompletableEmitter e) throws Exception {
                LoveDao loveDao = mMovieDatabase.getLoveDao();

                MovieLove movieLove = new MovieLove();
                movieLove.setMovieId(movieId);
                movieLove.setLoved(isLoved);

                long insert = loveDao.insert(movieLove);
                if (insert > 0) {
                    e.onComplete();
                } else {
                    e.onError(new Exception("Error inserting MovieLove with movieId = " + movieId));
                }
            }
        });
    }
}
