package br.com.luisfelipeas5.givemedetails.model.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieLove;
import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieTMDb;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface LoveDao {
    @Query("SELECT is_loved FROM movieLove WHERE movie_id = :movieId LIMIT 1")
    boolean isLoved(String movieId);

    @Insert(onConflict = REPLACE)
    long insert(MovieLove movie);
}
