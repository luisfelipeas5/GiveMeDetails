package br.com.luisfelipeas5.givemedetails.model.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieTMDb;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movies WHERE id = :id LIMIT 1")
    MovieTMDb getMovieById(String id);

    @Query("SELECT COUNT(id) FROM movies WHERE id LIKE :id")
    Integer getMovieByIdCount(String id);

    @Insert(onConflict = REPLACE)
    long insert(MovieTMDb movie);
}
