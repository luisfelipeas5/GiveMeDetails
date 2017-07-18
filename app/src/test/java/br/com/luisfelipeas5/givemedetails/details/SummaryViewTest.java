package br.com.luisfelipeas5.givemedetails.details;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.luisfelipeas5.givemedetails.presenter.details.SummaryMvpPresenter;
import br.com.luisfelipeas5.givemedetails.view.details.SummaryMvpView;
import br.com.luisfelipeas5.givemedetails.view.fragments.details.SummaryFragment;

import static org.mockito.Mockito.verify;

public class SummaryViewTest {

    private SummaryMvpView mSummaryMvpView;
    @Mock
    private SummaryMvpPresenter mSummaryMvpPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mSummaryMvpView = new SummaryFragment();
        ((SummaryFragment) mSummaryMvpView).setPresenter(mSummaryMvpPresenter);
    }

    @Test
    public void whenInstantiated_viewIsAttached_success() {
        verify(mSummaryMvpPresenter).attach(mSummaryMvpView);
    }

    @Test
    public void whenStop_viewIsDetached_success() {
        ((SummaryFragment) mSummaryMvpView).onStop();
        verify(mSummaryMvpPresenter).detachView();
    }

}
