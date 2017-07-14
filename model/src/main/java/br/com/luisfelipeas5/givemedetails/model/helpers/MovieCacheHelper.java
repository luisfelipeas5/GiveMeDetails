package br.com.luisfelipeas5.givemedetails.model.helpers;

import br.com.luisfelipeas5.givemedetails.model.model.Movie;
import io.reactivex.Single;

public class MovieCacheHelper implements MovieCacheMvpHelper{
    @Override
    public Single<Movie> getMovie(String movieId) {
        return null;
    }

    @Override
    public Single<Boolean> hasMoviePosterOnCache(String movieId) {
        return Single.just(false);
    }
}
