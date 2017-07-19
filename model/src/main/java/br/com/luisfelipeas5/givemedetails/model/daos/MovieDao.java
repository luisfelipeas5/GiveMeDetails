package br.com.luisfelipeas5.givemedetails.model.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.model.MovieTMDb;
import io.reactivex.Flowable;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movieTMDb WHERE id = :id LIMIT 1")
    Flowable<List<MovieTMDb>> getMovieById(String id);

    @Query("SELECT COUNT(id) FROM movieTMDb WHERE id = :id")
    Flowable<Integer> getMovieByIdCount(String id);

    @Insert
    void insert(MovieTMDb... movies);
}
