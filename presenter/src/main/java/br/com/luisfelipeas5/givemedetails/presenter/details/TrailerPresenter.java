package br.com.luisfelipeas5.givemedetails.presenter.details;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.model.trailer.Trailer;
import br.com.luisfelipeas5.givemedetails.presenter.BasePresenter;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.SchedulerProvider;
import br.com.luisfelipeas5.givemedetails.view.details.TrailersMvpView;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class TrailerPresenter extends BasePresenter<TrailersMvpView> implements TrailerMvpPresenter {
    private final MovieMvpDataManager mMovieMvpDataManager;
    private TrailersMvpView mTrailersMvpView;
    private boolean showSeeAllTrailersButton;

    public TrailerPresenter(SchedulerProvider schedulerProvider, MovieMvpDataManager movieMvpDataManager) {
        super(schedulerProvider);
        mMovieMvpDataManager = movieMvpDataManager;
    }

    @Override
    public void attach(TrailersMvpView trailersMvpView) {
        mTrailersMvpView = trailersMvpView;
    }

    @Override
    public void getTrailers(String movieId) {
        getTrailers(movieId, null);
    }

    @Override
    public void getTrailersPreview(String movieId) {
        Function<List<Trailer>, List<Trailer>> function = new Function<List<Trailer>, List<Trailer>>() {
            @Override
            public List<Trailer> apply(@NonNull List<Trailer> trailers) throws Exception {
                showSeeAllTrailersButton = true;
                if (trailers.size() > 3) {
                    return trailers.subList(0, 3);
                }
                return trailers;
            }
        };
        getTrailers(movieId, function);
    }

    private void getTrailers(String movieId, Function<List<Trailer>, List<Trailer>> mapper) {
        SchedulerProvider schedulerProvider = getSchedulerProvider();
        Single<List<Trailer>> listSingle = mMovieMvpDataManager.getMovieTrailers(movieId);
        if (mapper != null) {
            listSingle = listSingle.map(mapper);
        }
        listSingle.subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new SingleObserver<List<Trailer>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onSuccess(@NonNull List<Trailer> trailers) {
                        if (showSeeAllTrailersButton) {
                            mTrailersMvpView.showSeeAllTrailersButton();
                        }
                        mTrailersMvpView.onTrailersReady(trailers);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mTrailersMvpView.onGetTrailersFailed();
                    }
                });
    }

    @Override
    public void detachView() {
        mTrailersMvpView = new TrailersMvpView() {
            @Override
            public void setMovieId(String movieId, boolean showPreview) {

            }

            @Override
            public void onTrailersReady(List<Trailer> trailers) {

            }

            @Override
            public void onGetTrailersFailed() {

            }

            @Override
            public void showSeeAllTrailersButton() {

            }
        };
    }
}
