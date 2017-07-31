package br.com.luisfelipeas5.givemedetails.details;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.luisfelipeas5.givemedetails.presenter.details.MovieDetailMvpPresenter;
import br.com.luisfelipeas5.givemedetails.view.activities.DetailActivity;
import br.com.luisfelipeas5.givemedetails.view.details.MovieMvpView;

import static org.mockito.Mockito.verify;

public class DetailViewTest {

    private MovieMvpView mMovieMvpView;
    @Mock
    private MovieDetailMvpPresenter mMovieDetailMvpPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mMovieMvpView = new DetailActivity();
        ((DetailActivity) mMovieMvpView).setPresenter(mMovieDetailMvpPresenter);
    }

    @Test
    public void whenInstantiated_attachAndGetMovie_success() {
        verify(mMovieDetailMvpPresenter).attach(mMovieMvpView);
    }

}
