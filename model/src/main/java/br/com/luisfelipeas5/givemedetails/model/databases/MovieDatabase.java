package br.com.luisfelipeas5.givemedetails.model.databases;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import br.com.luisfelipeas5.givemedetails.model.daos.LoveDao;
import br.com.luisfelipeas5.givemedetails.model.daos.MovieDao;
import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieLove;
import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieTMDb;

@Database(entities = {MovieTMDb.class, MovieLove.class}, version = 2, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase{
    public abstract LoveDao getLoveDao();
    public abstract MovieDao getMovieDao();
}
