package br.com.luisfelipeas5.givemedetails.view.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import br.com.luisfelipeas5.givemedetails.model.databases.MovieCacheDatabase;
import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieDataManager;
import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
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
    MovieMvpDataManager provideMovieMvpDataManager(MovieApiMvpHelper movieApiMvpHelper,
                                                   MovieCacheMvpHelper movieCacheMvpHelper) {
        return new MovieDataManager(movieApiMvpHelper, movieCacheMvpHelper);
    }

}
