package br.com.luisfelipeas5.givemedetails.model.di;

import android.content.Context;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieDataManager;
import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieApiMvpHelper;
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
    MovieApiMvpHelper provideMovieApiMvpHelper() {
        return new TheMovieDbApiHelper(mContext);
    }

    @Provides
    MovieMvpDataManager provideMovieMvpDataManager(MovieApiMvpHelper movieApiMvpHelper) {
        return new MovieDataManager(movieApiMvpHelper);
    }

}
