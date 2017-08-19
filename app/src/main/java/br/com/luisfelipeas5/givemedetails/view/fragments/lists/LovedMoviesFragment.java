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
}
