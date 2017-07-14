package br.com.luisfelipeas5.givemedetails.view.di;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.presenter.details.MovieDetailMvpPresenter;
import br.com.luisfelipeas5.givemedetails.presenter.details.MovieDetailPresenter;
import br.com.luisfelipeas5.givemedetails.presenter.details.MoviePosterMvpPresenter;
import br.com.luisfelipeas5.givemedetails.presenter.details.MoviePosterPresenter;
import br.com.luisfelipeas5.givemedetails.presenter.list.MoviesMvpPresenter;
import br.com.luisfelipeas5.givemedetails.presenter.list.MoviesPresenter;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.AppSchedulerProvider;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.SchedulerProvider;
import dagger.Module;
import dagger.Provides;

@Module
class PresenterModule {

    @Provides
    static SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    static MoviesMvpPresenter provideMoviesMvpPresenter(MovieMvpDataManager movieMvpDataManager,
                                                        SchedulerProvider schedulerProvider) {
        return new MoviesPresenter(movieMvpDataManager, schedulerProvider);
    }

    @Provides
    static MovieDetailMvpPresenter provideMovieDetailMvpPresenter(MovieMvpDataManager movieMvpDataManager,
                                                                  SchedulerProvider schedulerProvider) {
        return new MovieDetailPresenter(movieMvpDataManager, schedulerProvider);
    }

    @Provides
    static MoviePosterMvpPresenter provideMoviePosterMvpPresenter(MovieMvpDataManager movieMvpDataManager,
                                                                  SchedulerProvider schedulerProvider) {
        return new MoviePosterPresenter(movieMvpDataManager, schedulerProvider);
    }

}
