package br.com.luisfelipeas5.givemedetails.model.helpers;

import io.reactivex.Completable;
import io.reactivex.Single;

public class DatabaseHelper implements DatabaseMvpHelper {

    @Override
    public Single<Boolean> isLoved(String movieId) {
        return null;
    }

    @Override
    public Completable setIsLoved(String movieId, boolean isLoved) {
        return null;
    }
}
