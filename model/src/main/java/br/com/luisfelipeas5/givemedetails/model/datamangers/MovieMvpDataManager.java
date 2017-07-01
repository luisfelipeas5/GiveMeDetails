package br.com.luisfelipeas5.givemedetails.model.datamangers;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.model.Movie;
import io.reactivex.Observable;

public interface MovieMvpDataManager {
    Observable<List<Movie>> getPopularMovies();
}
