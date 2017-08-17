package br.com.luisfelipeas5.givemedetails.model.helpers;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface DatabaseMvpHelper {
    Single<Boolean> isLoved(String movieId);

    Completable setIsLoved(String movieId, boolean isLoved);
}
