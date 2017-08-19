package br.com.luisfelipeas5.givemedetails.list;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.luisfelipeas5.givemedetails.presenter.list.MoviesMvpPresenter;
import br.com.luisfelipeas5.givemedetails.view.fragments.lists.LovedMoviesFragment;
import br.com.luisfelipeas5.givemedetails.view.list.MoviesMvpView;

import static org.mockito.Mockito.verify;

public class LovedMoviesViewTest {

    private MoviesMvpView mLovedMoviesView;
    @Mock
    private MoviesMvpPresenter mMoviesMvpPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mLovedMoviesView = new LovedMoviesFragment();
        ((LovedMoviesFragment) mLovedMoviesView).setPresenter(mMoviesMvpPresenter);
        verify(mMoviesMvpPresenter).attach(mLovedMoviesView);
    }

    @Test
    public void callPresenterMethod_whenOnGetMovies_success() {
        mLovedMoviesView.onGetMovies();
        verify(mMoviesMvpPresenter).getLovedMovies();
    }

}
