package br.com.luisfelipeas5.givemedetails.view.di.modules.model;

import android.arch.persistence.room.Room;
import android.content.Context;

import br.com.luisfelipeas5.givemedetails.model.contentproviders.MovieContentProvider;
import br.com.luisfelipeas5.givemedetails.model.contentproviders.MovieMvpContentProvider;
import br.com.luisfelipeas5.givemedetails.model.databases.MovieCacheDatabase;
import br.com.luisfelipeas5.givemedetails.model.databases.MovieDatabase;
import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieDataManager;
import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.helpers.DatabaseHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.DatabaseMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieApiMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieCacheHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieCacheMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.TheMovieDbApiHelper;
import dagger.Module;
import dagger.Provides;

@Module
public class ModelModule {

    private Context mContext;

    public ModelModule(Context context) {
        mContext = context;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }

    @Provides
    MovieApiMvpHelper provideMovieApiMvpHelper(Context context) {
        return new TheMovieDbApiHelper(context);
    }

    @Provides
    MovieCacheDatabase provideMovieCacheDatabase() {
        return Room.databaseBuilder(mContext.getApplicationContext(), MovieCacheDatabase.class, "cache_database").build();
    }

    @Provides
    MovieCacheMvpHelper provideMovieCacheMvpHelper(MovieCacheDatabase movieCacheDatabase) {
        return new MovieCacheHelper(movieCacheDatabase);
    }

    @Provides
    MovieDatabase provideMovieDatabase() {
        return Room.databaseBuilder(mContext.getApplicationContext(),
                MovieDatabase.class,
                "database").build();
    }

    @Provides
    MovieMvpContentProvider provideMovieMvpContentProvider(MovieDatabase movieDatabase) {
        return new MovieContentProvider(movieDatabase, mContext);
    }

    @Provides
    DatabaseMvpHelper provideDatabaseMvpHelper(MovieMvpContentProvider movieMvpContentProvider) {
        return new DatabaseHelper(movieMvpContentProvider);
    }

    @Provides
    MovieMvpDataManager provideMovieMvpDataManager(MovieApiMvpHelper movieApiMvpHelper,
                                                   MovieCacheMvpHelper movieCacheMvpHelper, DatabaseMvpHelper databaseMvpHelper) {
        return new MovieDataManager(movieApiMvpHelper, movieCacheMvpHelper, databaseMvpHelper);
    }

}
