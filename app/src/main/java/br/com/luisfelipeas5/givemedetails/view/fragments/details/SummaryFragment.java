package br.com.luisfelipeas5.givemedetails.view.fragments.details;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import br.com.luisfelipeas5.givemedetails.databinding.FragmentDetailBinding;
import br.com.luisfelipeas5.givemedetails.presenter.details.SummaryMvpPresenter;
import br.com.luisfelipeas5.givemedetails.view.MoviesApp;
import br.com.luisfelipeas5.givemedetails.view.details.SummaryMvpView;
import br.com.luisfelipeas5.givemedetails.view.di.AppComponent;

public class SummaryFragment extends Fragment implements SummaryMvpView {

    private SummaryMvpPresenter mPresenter;

    public SummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentDetailBinding mBinding = FragmentDetailBinding.inflate(inflater, container, false);
        MoviesApp moviesApp = (MoviesApp) getContext().getApplicationContext();
        AppComponent appComponent = moviesApp.getAppComponent();
        appComponent.inject(this);
        return mBinding.getRoot();
    }

    public void setMovieId(String movieId) {
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
    public void onTitleReady(String title) {

    }

    @Override
    public void onSummaryFailed() {

    }
}
