package br.com.luisfelipeas5.givemedetails.ui.fragments.movielists;

import br.com.luisfelipeas5.givemedetails.R;
import br.com.luisfelipeas5.givemedetails.api.GiveMeDetailsApi;
import br.com.luisfelipeas5.givemedetails.api.responsebodies.MoviesResponseBody;
import br.com.luisfelipeas5.givemedetails.api.tasks.GetMoviesTask;

public class PopularMoviesFragment extends MoviesFragment {
    @Override
    public GetMoviesTask getMovies(GiveMeDetailsApi.Callback<MoviesResponseBody> callback) {
        return GiveMeDetailsApi.getPopularMovies(getContext(), callback);
    }

    @Override
    public int getTitleResource() {
        return R.string.popular_movies;
    }
}
