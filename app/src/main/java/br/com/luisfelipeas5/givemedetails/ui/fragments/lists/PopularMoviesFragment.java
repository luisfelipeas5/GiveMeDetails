package br.com.luisfelipeas5.givemedetails.ui.fragments.lists;

import br.com.luisfelipeas5.givemedetails.R;
import br.com.luisfelipeas5.givemedetails.view.list.MoviesMvpView;

public class PopularMoviesFragment extends MoviesFragment implements MoviesMvpView {

    @Override
    public int getTitleResource() {
        return R.string.popular_movies;
    }

    @Override
    public void onGetMovies() {
        mPresenter.getPopularMovies();
    }
}
