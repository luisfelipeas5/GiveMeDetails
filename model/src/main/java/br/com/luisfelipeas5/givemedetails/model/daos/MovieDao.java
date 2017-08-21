package br.com.luisfelipeas5.givemedetails.model.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieTMDb;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM " + MovieTMDb.TABLE_NAME + " WHERE id = :id LIMIT 1")
    MovieTMDb getMovieById(String id);

    @Query("SELECT * FROM " + MovieTMDb.TABLE_NAME + " WHERE id = :id LIMIT 1")
    Cursor getMovieByIdCursor(String id);

    @Query("SELECT COUNT(id) FROM " + MovieTMDb.TABLE_NAME + " WHERE id LIKE :id")
    Integer getMovieByIdCount(String id);

    @Insert(onConflict = REPLACE)
    long insert(MovieTMDb movie);

    @Query("SELECT * FROM " + MovieTMDb.TABLE_NAME)
    Cursor getMovies();

    @Query("DELETE FROM " + MovieTMDb.TABLE_NAME + " WHERE id = :id")
    int deleteById(String id);

    @Update
    int update(MovieTMDb movieTMDb);

    @Query("DELETE FROM " + MovieTMDb.TABLE_NAME)
    int deleteAll();
}
