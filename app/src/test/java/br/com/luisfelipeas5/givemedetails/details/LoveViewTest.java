package br.com.luisfelipeas5.givemedetails.details;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.luisfelipeas5.givemedetails.presenter.details.LoveMvpPresenter;
import br.com.luisfelipeas5.givemedetails.view.details.LoveMvpView;
import br.com.luisfelipeas5.givemedetails.view.fragments.details.LoveFragment;

import static org.mockito.Mockito.verify;

public class LoveViewTest {

    private static final String MOVIE_ID_MOCKED = "Movie Id mocked";
    private LoveMvpView mLoveMvpView;
    @Mock
    private LoveMvpPresenter mLoveMvpPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mLoveMvpView = new LoveFragment();
        ((LoveFragment) mLoveMvpView).setPresenter(mLoveMvpPresenter);
        verify(mLoveMvpPresenter).attach(mLoveMvpView);

        mLoveMvpView.setMovieId(MOVIE_ID_MOCKED);
        verify(mLoveMvpPresenter).onMovieIdReady(MOVIE_ID_MOCKED);
    }

    @Test
    public void whenOnLoveClick_callLoveOfPresenter_success() {
        mLoveMvpView.onLoveClick();
        verify(mLoveMvpPresenter).loveMovieById(MOVIE_ID_MOCKED);
    }
}
