package br.com.luisfelipeas5.givemedetails.model.helpers;

import br.com.luisfelipeas5.givemedetails.model.model.MoviesResponseBody;
import io.reactivex.Observable;

public interface TheMovieDBMvpHelper {
    Observable<MoviesResponseBody> getPopular();
}
