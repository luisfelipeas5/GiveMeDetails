package br.com.luisfelipeas5.givemedetails.details;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.luisfelipeas5.givemedetails.presenter.details.PosterMvpPresenter;
import br.com.luisfelipeas5.givemedetails.view.details.MoviePosterMvpView;
import br.com.luisfelipeas5.givemedetails.view.fragments.details.PosterFragment;

import static org.mockito.Mockito.verify;

public class MoviePosterViewTest {

    private MoviePosterMvpView mMovieMvpView;
    @Mock
    private PosterMvpPresenter mMovieDetailMvpPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mMovieMvpView = new PosterFragment();
        ((PosterFragment) mMovieMvpView).setPresenter(mMovieDetailMvpPresenter);
    }

    @Test
    public void viewAttached_success() {
        verify(mMovieDetailMvpPresenter).attach(mMovieMvpView);
    }

    @Test
    public void whenViewStop_detachFromPresenter_success() {
        ((PosterFragment) mMovieMvpView).onStop();
        verify(mMovieDetailMvpPresenter).detachView();
    }

}
