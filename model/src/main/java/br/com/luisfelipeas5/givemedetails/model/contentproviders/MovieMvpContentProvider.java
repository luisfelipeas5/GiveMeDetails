package br.com.luisfelipeas5.givemedetails.model.contentproviders;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieLove;
import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieTMDb;

public interface MovieMvpContentProvider {
    Boolean isLoved(String movieId);

    Integer getMovieByIdCount(String movieId);

    long insert(MovieTMDb movieTMDb);

    List<MovieTMDb> getLoved();

    long insert(MovieLove movieLove);

    MovieTMDb getMovieById(String movieId);
}
