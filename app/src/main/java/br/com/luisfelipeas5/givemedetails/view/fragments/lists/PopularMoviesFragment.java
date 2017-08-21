package br.com.luisfelipeas5.givemedetails.view.fragments.lists;

import br.com.luisfelipeas5.givemedetails.R;
import br.com.luisfelipeas5.givemedetails.view.list.MoviesMvpView;

public class PopularMoviesFragment extends MoviesFragment implements MoviesMvpView {

    @Override
    protected boolean needsNetworkConnection() {
        return true;
    }

    @Override
    public int getTitleResource() {
        return R.string.popular_movies;
    }

    @Override
    public void onGetMovies() {
        mPresenter.getPopularMovies();
    }
}
