package br.com.luisfelipeas5.givemedetails.model.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieLove;
import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieTMDb;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface LoveDao {
    @Query("SELECT " + MovieLove.COLUMN_IS_LOVED +
            " FROM " + MovieLove.TABLE_NAME +
            " WHERE " + MovieLove.COLUMN_MOVIE_ID +" = :movieId LIMIT 1")
    boolean isLoved(String movieId);

    @Query("SELECT " + MovieLove.COLUMN_IS_LOVED +
            " FROM " + MovieLove.TABLE_NAME +
            " WHERE " + MovieLove.COLUMN_MOVIE_ID +" = :movieId LIMIT 1")
    Cursor isLovedCursor(String movieId);

    @Insert(onConflict = REPLACE)
    long insert(MovieLove movieLove);

    @Query("SELECT *" +
            " FROM "+ MovieTMDb.TABLE_NAME +
            " INNER JOIN " + MovieLove.TABLE_NAME +
            " ON " + MovieLove.TABLE_NAME + "." + MovieLove.COLUMN_MOVIE_ID + " = " + MovieTMDb.TABLE_NAME + "." + MovieTMDb.COLUMN_ID +
            " WHERE " + MovieLove.COLUMN_IS_LOVED + " LIKE :isLoved")
    List<MovieTMDb> getLoved(boolean isLoved);

    @Query("SELECT *" +
            " FROM "+ MovieTMDb.TABLE_NAME +
            " INNER JOIN " + MovieLove.TABLE_NAME +
            " ON " + MovieLove.TABLE_NAME + "." + MovieLove.COLUMN_MOVIE_ID + " = " + MovieTMDb.TABLE_NAME + "." + MovieTMDb.COLUMN_ID +
            " WHERE " + MovieLove.COLUMN_IS_LOVED + " LIKE :isLoved")
    Cursor getLovedCursor(boolean isLoved);
}
