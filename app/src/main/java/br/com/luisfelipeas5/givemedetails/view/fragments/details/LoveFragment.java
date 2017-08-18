package br.com.luisfelipeas5.givemedetails.view.fragments.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import br.com.luisfelipeas5.givemedetails.R;
import br.com.luisfelipeas5.givemedetails.databinding.FragmentLoveBinding;
import br.com.luisfelipeas5.givemedetails.presenter.details.LoveMvpPresenter;
import br.com.luisfelipeas5.givemedetails.view.MoviesApp;
import br.com.luisfelipeas5.givemedetails.view.details.LoveMvpView;

public class LoveFragment extends Fragment implements LoveMvpView, View.OnClickListener {
    private LoveMvpPresenter presenter;
    private String mMovieId;
    private FragmentLoveBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentLoveBinding.inflate(inflater, container, false);
        mBinding.buttonLove.setOnClickListener(this);
        mBinding.buttonLove.setVisibility(View.INVISIBLE);

        MoviesApp moviesApp =
                (MoviesApp) getContext().getApplicationContext();
        moviesApp.getDiComponent().inject(this);

        return mBinding.getRoot();
    }

    @Inject
    public void setPresenter(LoveMvpPresenter presenter) {
        this.presenter = presenter;
        this.presenter.attach(this);
    }

    @Override
    public void setMovieId(String movieId) {
        mMovieId = movieId;
        mBinding.buttonLove.setVisibility(View.VISIBLE);
        mBinding.progressBar.setVisibility(View.GONE);
        this.presenter.loveMovieById(movieId);
    }

    @Override
    public void onLoveClick() {
        presenter.loveMovieById(mMovieId);
    }

    @Override
    public void onMovieLoved(boolean withLove) {
        int imageResource = R.drawable.ic_hearth;
        if (withLove) {
            imageResource = R.drawable.ic_hearth_filled;
        }
        mBinding.buttonLove.setImageResource(imageResource);
        mBinding.buttonLove.setContentDescription(Boolean.toString(withLove));
    }

    @Override
    public void onLoveFailed() {

    }

    @Override
    public void onIsLovingMovie(boolean isLoving) {
        int progressVisibility = View.GONE;
        if (isLoving) {
            progressVisibility = View.VISIBLE;
            mBinding.buttonLove.setImageResource(R.color.transparent);
        }
        mBinding.progressBar.setVisibility(progressVisibility);
    }

    @Override
    public String getMovieId() {
        return mMovieId;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.button_love) {
            onLoveClick();
        }
    }
}
