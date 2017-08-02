package br.com.luisfelipeas5.givemedetails.view.fragments.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import br.com.luisfelipeas5.givemedetails.adapters.recyclers.TrailersAdapter;
import br.com.luisfelipeas5.givemedetails.databinding.FragmentTrailersBinding;
import br.com.luisfelipeas5.givemedetails.model.model.trailer.Trailer;
import br.com.luisfelipeas5.givemedetails.presenter.details.TrailerMvpPresenter;
import br.com.luisfelipeas5.givemedetails.view.MoviesApp;
import br.com.luisfelipeas5.givemedetails.view.details.TrailersMvpView;
import br.com.luisfelipeas5.givemedetails.view.di.components.BaseComponent;

public class TrailersFragment extends Fragment implements TrailersMvpView {

    private FragmentTrailersBinding mBinding;
    private TrailerMvpPresenter mTrailerMvpPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentTrailersBinding.inflate(inflater, container, false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBinding.recyclerTrailers.setLayoutManager(layoutManager);

        MoviesApp moviesApp = (MoviesApp) getContext().getApplicationContext();
        BaseComponent baseComponent = moviesApp.getDiComponent();
        baseComponent.inject(this);

        return mBinding.getRoot();
    }

    @Inject
    public void setPresenter(TrailerMvpPresenter trailerMvpPresenter) {
        mTrailerMvpPresenter = trailerMvpPresenter;
        mTrailerMvpPresenter.attach(this);
    }

    @Override
    public void setMovieId(String movieId) {
        mTrailerMvpPresenter.getTrailers(movieId);
    }

    @Override
    public void onTrailersReady(List<Trailer> trailers) {
        TrailersAdapter trailersAdapter = new TrailersAdapter(trailers);
        mBinding.recyclerTrailers.setAdapter(trailersAdapter);
    }

    @Override
    public void onGetTrailersFailed() {

    }

    @Override
    public void onStop() {
        mTrailerMvpPresenter.detachView();
        super.onStop();
    }
}
