package br.com.luisfelipeas5.givemedetails.presenter.di;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.presenter.list.MoviesMvpPresenter;
import br.com.luisfelipeas5.givemedetails.presenter.list.MoviesPresenter;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.AppSchedulerProvider;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.SchedulerProvider;
import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    MoviesMvpPresenter provideMoviesMvpPresenter(MovieMvpDataManager movieMvpDataManager,
                                                 SchedulerProvider schedulerProvider) {
        return new MoviesPresenter(movieMvpDataManager, schedulerProvider);
    }

}
