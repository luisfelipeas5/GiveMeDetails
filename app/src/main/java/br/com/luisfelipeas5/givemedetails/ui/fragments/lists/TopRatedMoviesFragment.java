package br.com.luisfelipeas5.givemedetails.ui.fragments.lists;

import br.com.luisfelipeas5.givemedetails.R;

public class TopRatedMoviesFragment extends MoviesFragment {

    @Override
    public int getTitleResource() {
        return R.string.top_rated_movies;
    }

    @Override
    public void onGetMovies() {
        mPresenter.getTopRatedMovies();
    }
}
