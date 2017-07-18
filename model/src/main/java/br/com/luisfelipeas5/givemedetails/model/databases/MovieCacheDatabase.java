package br.com.luisfelipeas5.givemedetails.model.databases;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import br.com.luisfelipeas5.givemedetails.model.daos.MovieDao;
import br.com.luisfelipeas5.givemedetails.model.model.MovieTMDb;

@Database(entities = MovieTMDb.class, version = 1)
public abstract class MovieCacheDatabase extends RoomDatabase{
    public abstract MovieDao getMovieDao();
}
