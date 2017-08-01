package br.com.luisfelipeas5.givemedetails.details;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.luisfelipeas5.givemedetails.presenter.details.SocialMvpPresenter;
import br.com.luisfelipeas5.givemedetails.view.details.SocialMvpView;
import br.com.luisfelipeas5.givemedetails.view.fragments.details.SocialFragment;

import static org.mockito.Mockito.verify;

public class SocialViewTest {

    private SocialMvpView mSocialMvpView;

    @Mock
    public SocialMvpPresenter mSocialMvpPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mSocialMvpView = new SocialFragment();
        ((SocialFragment) mSocialMvpView).setPresenter(mSocialMvpPresenter);
    }

    @Test
    public void whenSetPresenter_attachView_success() {
        verify(mSocialMvpPresenter).attach(mSocialMvpView);
    }

    @Test
    public void whenStop_detachView_success() {
        ((SocialFragment) mSocialMvpView).onStop();
        verify(mSocialMvpPresenter).detachView();
    }

}
