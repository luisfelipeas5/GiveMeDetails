package br.com.luisfelipeas5.givemedetails.view.fragments.details;

import android.content.Intent;
import android.net.Uri;
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

import br.com.luisfelipeas5.givemedetails.R;
import br.com.luisfelipeas5.givemedetails.adapters.recyclers.TrailersAdapter;
import br.com.luisfelipeas5.givemedetails.databinding.FragmentTrailersBinding;
import br.com.luisfelipeas5.givemedetails.model.model.trailer.Trailer;
import br.com.luisfelipeas5.givemedetails.presenter.details.TrailerMvpPresenter;
import br.com.luisfelipeas5.givemedetails.view.MoviesApp;
import br.com.luisfelipeas5.givemedetails.view.details.TrailersMvpView;
import br.com.luisfelipeas5.givemedetails.view.di.components.BaseComponent;

public class TrailersFragment extends Fragment implements TrailersMvpView, TrailersAdapter.OnTrailerClickListener, View.OnClickListener {

    private FragmentTrailersBinding mBinding;
    private TrailerMvpPresenter mTrailerMvpPresenter;
    private Listener listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentTrailersBinding.inflate(inflater, container, false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBinding.recyclerTrailers.setLayoutManager(layoutManager);
        mBinding.recyclerTrailers.setNestedScrollingEnabled(false);

        mBinding.buttonSeeAllTrailers.setOnClickListener(this);

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
    public void setMovieId(String movieId, boolean showPreview) {
        if (showPreview) {
            mTrailerMvpPresenter.getTrailersPreview(movieId);
        } else {
            mTrailerMvpPresenter.getTrailers(movieId);
        }
    }

    @Override
    public void onTrailersReady(List<Trailer> trailers) {
        int recyclerTrailersVisibility = View.GONE;
        int txtWarningVisibility = View.VISIBLE;
        if (trailers != null && !trailers.isEmpty()) {
            TrailersAdapter trailersAdapter = new TrailersAdapter(trailers);
            trailersAdapter.setListener(this);
            mBinding.recyclerTrailers.setAdapter(trailersAdapter);

            recyclerTrailersVisibility = View.VISIBLE;
            txtWarningVisibility = View.GONE;
        }
        mBinding.progressBar.setVisibility(View.GONE);
        mBinding.recyclerTrailers.setVisibility(recyclerTrailersVisibility);
        mBinding.txtNoTrailers.setVisibility(txtWarningVisibility);
    }

    @Override
    public void onGetTrailersFailed() {
        mBinding.progressBar.setVisibility(View.GONE);
        mBinding.recyclerTrailers.setVisibility(View.GONE);
        mBinding.txtNoTrailers.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSeeAllTrailersButton() {
        mBinding.buttonSeeAllTrailers.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStop() {
        mTrailerMvpPresenter.detachView();
        super.onStop();
    }

    @Override
    public void onTrailerClick(Trailer trailer) {
        Uri trailerUri = Uri.parse(trailer.getIntentUri());
        Intent intent = new Intent(Intent.ACTION_VIEW, trailerUri);
        startActivity(intent);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.button_see_all_trailers) {
            if (listener != null) {
                listener.onSeeAllTrailersClicked();
            }
        }
    }

    public interface Listener {
        void onSeeAllTrailersClicked();
    }
}
