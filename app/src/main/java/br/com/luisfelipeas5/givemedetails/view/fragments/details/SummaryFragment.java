package br.com.luisfelipeas5.givemedetails.view.fragments.details;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import br.com.luisfelipeas5.givemedetails.databinding.FragmentSummaryBinding;
import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import br.com.luisfelipeas5.givemedetails.presenter.details.SummaryMvpPresenter;
import br.com.luisfelipeas5.givemedetails.view.MoviesApp;
import br.com.luisfelipeas5.givemedetails.view.details.SummaryMvpView;
import br.com.luisfelipeas5.givemedetails.view.di.components.BaseComponent;

public class SummaryFragment extends Fragment implements SummaryMvpView {

    private SummaryMvpPresenter mPresenter;
    private FragmentSummaryBinding mBinding;
    private String mMovieId;

    public SummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentSummaryBinding.inflate(inflater, container, false);
        MoviesApp moviesApp = (MoviesApp) getContext().getApplicationContext();
        BaseComponent appComponent = moviesApp.getDiComponent();
        appComponent.inject(this);

        if (mMovieId != null) {
            setMovieId(mMovieId);
        }

        return mBinding.getRoot();
    }

    public void setMovieId(String movieId) {
        mMovieId = movieId;
        if (mBinding != null) {
            mPresenter.getSummary(movieId);
            mBinding.txtNoSummary.setVisibility(View.GONE);
            mBinding.layoutContent.setVisibility(View.VISIBLE);
        }
    }

    @Inject
    public void setPresenter(SummaryMvpPresenter presenter) {
        mPresenter = presenter;
        mPresenter.attach(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.detachView();
    }

    @Override
    public void onSummaryFailed() {
        mBinding.txtNoSummary.setVisibility(View.VISIBLE);
        mBinding.layoutContent.setVisibility(View.GONE);
    }

    @Override
    public void onMovieSummaryReady(Movie mMovie) {
        mBinding.setMovie(mMovie);
    }

}
