package br.com.luisfelipeas5.givemedetails.view.fragments.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import br.com.luisfelipeas5.givemedetails.databinding.FragmentSocialBinding;
import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import br.com.luisfelipeas5.givemedetails.presenter.details.SocialMvpPresenter;
import br.com.luisfelipeas5.givemedetails.view.MoviesApp;
import br.com.luisfelipeas5.givemedetails.view.details.SocialMvpView;

public class SocialFragment extends Fragment implements SocialMvpView {

    private FragmentSocialBinding mBinding;
    private SocialMvpPresenter mPresenter;

    public SocialFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentSocialBinding.inflate(inflater, container, false);

        MoviesApp moviesApp = (MoviesApp) getContext().getApplicationContext();
        moviesApp.getDiComponent().inject(this);

        return mBinding.getRoot();
    }

    @Inject
    public void setPresenter(SocialMvpPresenter presenter) {
        mPresenter = presenter;
        mPresenter.attach(this);
    }

    @Override
    public void onStop() {
        mPresenter.detachView();
        super.onStop();
    }

    @Override
    public void onMovieSocialReady(Movie movie) {
        mBinding.setMovie(movie);
    }

    @Override
    public void onMovieSocialFailed() {

    }

    public void setMovieId(String movieId) {
        mPresenter.getSocialById(movieId);
    }
}
