package br.com.luisfelipeas5.givemedetails.view.fragments.lists;

import br.com.luisfelipeas5.givemedetails.R;

public class LovedMoviesFragment extends MoviesFragment {

    @Override
    public int getTitleResource() {
        return R.string.loved_movies;
    }

    @Override
    public void onGetMovies() {
        mPresenter.getLovedMovies();
    }

    @Override
    protected int getNoMoviesMessage() {
        return R.string.no_loved_movies;
    }

    @Override
    protected boolean needsNetworkConnection() {
        return false;
    }

    @Override
    protected int getNoNetworkMessage() {
        return getNoMoviesMessage();
    }

    @Override
    public void onStart() {
        super.onStart();
        onRefresh();
    }
}
