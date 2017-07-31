package br.com.luisfelipeas5.givemedetails.presenter.details;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.model.Movie;
import br.com.luisfelipeas5.givemedetails.presenter.BasePresenter;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.SchedulerProvider;
import br.com.luisfelipeas5.givemedetails.view.details.SocialMvpView;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class SocialPresenter extends BasePresenter<SocialMvpView> implements SocialMvpPresenter {
    private SocialMvpView mSocialMvpView;
    private MovieMvpDataManager mMovieMvpDataManager;

    public SocialPresenter(SchedulerProvider schedulerProvider, MovieMvpDataManager movieMvpDataManager) {
        super(schedulerProvider);
        mMovieMvpDataManager = movieMvpDataManager;
    }

    @Override
    public void attach(SocialMvpView socialMvpView) {
        mSocialMvpView = socialMvpView;
    }

    @Override
    public void detachView() {
        mSocialMvpView = new SocialMvpView() {
            @Override
            public void onMovieSocialReady(Movie movie) {

            }

            @Override
            public void onMovieSocialFailed() {

            }
        };
    }

    @Override
    public void getSocialById(String id) {
        SchedulerProvider schedulerProvider = getSchedulerProvider();
        mMovieMvpDataManager.getMovieSocial(id)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .subscribe(new SingleObserver<Movie>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }
                    @Override
                    public void onSuccess(@NonNull Movie movie) {
                        mSocialMvpView.onMovieSocialReady(movie);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mSocialMvpView.onMovieSocialFailed();
                    }
                });
    }
}
