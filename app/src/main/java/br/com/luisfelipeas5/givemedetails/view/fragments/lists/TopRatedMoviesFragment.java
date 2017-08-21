package br.com.luisfelipeas5.givemedetails.view.fragments.lists;

import br.com.luisfelipeas5.givemedetails.R;

public class TopRatedMoviesFragment extends MoviesFragment {

    @Override
    protected boolean needsNetworkConnection() {
        return true;
    }

    @Override
    public int getTitleResource() {
        return R.string.top_rated_movies;
    }

    @Override
    public void onGetMovies() {
        mPresenter.getTopRatedMovies();
    }
}
