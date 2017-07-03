package br.com.luisfelipeas5.givemedetails.list;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.luisfelipeas5.givemedetails.presenter.list.MoviesMvpPresenter;
import br.com.luisfelipeas5.givemedetails.ui.fragments.lists.PopularMoviesFragment;
import br.com.luisfelipeas5.givemedetails.view.list.MoviesMvpView;

import static org.mockito.Mockito.verify;

public class MoviesPopularViewTest {

    private MoviesMvpView mPopularView;
    @Mock
    private MoviesMvpPresenter mMoviesMvpPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mPopularView = new PopularMoviesFragment();
        ((PopularMoviesFragment) mPopularView).setPresenter(mMoviesMvpPresenter);
    }

    @Test
    public void callPresenterMethod_whenOnGetMovies_success() {
        mPopularView.onGetMovies();
        verify(mMoviesMvpPresenter).getPopularMovies();
    }

}
