package br.com.luisfelipeas5.givemedetails.view.fragments.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import br.com.luisfelipeas5.givemedetails.R;
import br.com.luisfelipeas5.givemedetails.databinding.FragmentDetailPosterBinding;
import br.com.luisfelipeas5.givemedetails.presenter.details.PosterMvpPresenter;
import br.com.luisfelipeas5.givemedetails.view.MoviesApp;
import br.com.luisfelipeas5.givemedetails.view.details.MoviePosterMvpView;
import br.com.luisfelipeas5.givemedetails.view.di.components.BaseComponent;

public class PosterFragment extends Fragment implements MoviePosterMvpView {
    private PosterMvpPresenter mPresenter;
    private FragmentDetailPosterBinding mBinding;
    private String mMovieId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentDetailPosterBinding.inflate(inflater, container, false);

        MoviesApp moviesApp = (MoviesApp) getContext().getApplicationContext();
        BaseComponent appComponent = moviesApp.getDiComponent();
        appComponent.inject(this);
        getPosterWidth();

        return mBinding.getRoot();
    }

    @Override
    public void onMoviePosterUrlReady(String posterUrl) {
        Glide.with(getContext())
                .load(posterUrl)
                .centerCrop()
                .into(mBinding.imgMoviePoster);
    }

    @Override
    public void onGetMoviePosterUrlFailed() {

    }

    public void setMovieId(String movieId) {
        mMovieId = movieId;
        getPosterWidth();
        mPresenter.getMovieTitle(movieId);
    }

    @Override
    public void getPosterWidth() {
        if (mMovieId != null) {
            if (mBinding != null) {
                int width = mBinding.imgMoviePoster.getWidth();
                if (width <= 0) {
                    mBinding.imgMoviePoster.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            mBinding.imgMoviePoster.getViewTreeObserver().removeOnPreDrawListener(this);
                            getPosterWidth();
                            return true;
                        }
                    });
                } else {
                    mPresenter.getMoviePosterUrl(mMovieId, width);
                }
            }
        }
    }

    @Override
    public void onGetMovieTitleReady(String movieTitle) {
        if (mBinding != null) {
            String contentDescription = getString(R.string.movie_poster_description, movieTitle);
            mBinding.imgMoviePoster.setContentDescription(contentDescription);
        }
    }

    @Override
    public void onGetMovieTitleFailed() {

    }

    @Inject
    public void setPresenter(PosterMvpPresenter presenter) {
        mPresenter = presenter;
        mPresenter.attach(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.detachView();
    }
}
