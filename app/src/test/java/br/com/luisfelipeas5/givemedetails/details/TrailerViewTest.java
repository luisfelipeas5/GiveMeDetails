package br.com.luisfelipeas5.givemedetails.details;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.luisfelipeas5.givemedetails.presenter.details.TrailerMvpPresenter;
import br.com.luisfelipeas5.givemedetails.view.details.TrailersMvpView;
import br.com.luisfelipeas5.givemedetails.view.fragments.details.TrailersFragment;

import static org.mockito.Mockito.verify;

public class TrailerViewTest {

    private TrailersMvpView mTrailersMvpView;
    @Mock
    private TrailerMvpPresenter mTrailersMvpPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mTrailersMvpView = new TrailersFragment();
        ((TrailersFragment) mTrailersMvpView).setPresenter(mTrailersMvpPresenter);
        verify(mTrailersMvpPresenter).attach(mTrailersMvpView);
    }

    @Test
    public void whenSetMovieId_callPresenter_success() {
        String movieId = "Movie Id mocked";
        mTrailersMvpView.setMovieId(movieId);
        verify(mTrailersMvpPresenter).getTrailers(movieId);
    }

    @Test
    public void whenOnStop_detachView_success() {
        ((TrailersFragment) mTrailersMvpView).onStop();
        verify(mTrailersMvpPresenter).detachView();
    }

}
