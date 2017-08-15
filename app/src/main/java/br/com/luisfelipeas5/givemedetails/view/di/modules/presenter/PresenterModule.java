package br.com.luisfelipeas5.givemedetails.view.di.modules.presenter;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.presenter.details.MovieDetailMvpPresenter;
import br.com.luisfelipeas5.givemedetails.presenter.details.MovieDetailPresenter;
import br.com.luisfelipeas5.givemedetails.presenter.details.PosterMvpPresenter;
import br.com.luisfelipeas5.givemedetails.presenter.details.PosterPresenter;
import br.com.luisfelipeas5.givemedetails.presenter.details.ReviewsMvpPresenter;
import br.com.luisfelipeas5.givemedetails.presenter.details.ReviewsPresenter;
import br.com.luisfelipeas5.givemedetails.presenter.details.SocialMvpPresenter;
import br.com.luisfelipeas5.givemedetails.presenter.details.SocialPresenter;
import br.com.luisfelipeas5.givemedetails.presenter.details.SummaryMvpPresenter;
import br.com.luisfelipeas5.givemedetails.presenter.details.SummaryPresenter;
import br.com.luisfelipeas5.givemedetails.presenter.details.TrailerMvpPresenter;
import br.com.luisfelipeas5.givemedetails.presenter.details.TrailerPresenter;
import br.com.luisfelipeas5.givemedetails.presenter.list.MoviesMvpPresenter;
import br.com.luisfelipeas5.givemedetails.presenter.list.MoviesPresenter;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.AppSchedulerProvider;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.SchedulerProvider;
import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

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
    static PosterMvpPresenter provideMoviePosterMvpPresenter(MovieMvpDataManager movieMvpDataManager,
                                                             SchedulerProvider schedulerProvider) {
        return new PosterPresenter(movieMvpDataManager, schedulerProvider);
    }

    @Provides
    static SummaryMvpPresenter provideSummaryMvpPresenter(MovieMvpDataManager movieMvpDataManager,
                                                          SchedulerProvider schedulerProvider) {
        return new SummaryPresenter(movieMvpDataManager, schedulerProvider);
    }

    @Provides
    static SocialMvpPresenter provideSocialMvpPresenter(SchedulerProvider schedulerProvider, MovieMvpDataManager movieMvpDataManager) {
        return new SocialPresenter(schedulerProvider, movieMvpDataManager);
    }

    @Provides
    static TrailerMvpPresenter provideTrailerMvpPresenter(SchedulerProvider schedulerProvider, MovieMvpDataManager movieMvpDataManager) {
        return new TrailerPresenter(schedulerProvider, movieMvpDataManager);
    }

    @Provides
    static ReviewsMvpPresenter provideReviewsMvpPresenter(SchedulerProvider schedulerProvider, MovieMvpDataManager movieMvpDataManager) {
        return new ReviewsPresenter(schedulerProvider, movieMvpDataManager);
    }
}
