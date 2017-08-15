package br.com.luisfelipeas5.givemedetails.details;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.luisfelipeas5.givemedetails.presenter.details.ReviewsMvpPresenter;
import br.com.luisfelipeas5.givemedetails.view.details.ReviewsMvpView;
import br.com.luisfelipeas5.givemedetails.view.fragments.details.ReviewsFragment;

import static org.mockito.Mockito.verify;

public class ReviewsViewTest {

    private static final String MOVIE_ID = "Movie Id Mocked";
    private ReviewsMvpView mReviewsMvpView;

    @Mock
    private ReviewsMvpPresenter mReviewsMvpPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mReviewsMvpView = new ReviewsFragment();
        ((ReviewsFragment) mReviewsMvpView).setPresenter(mReviewsMvpPresenter);
        verify(mReviewsMvpPresenter).attach(mReviewsMvpView);
    }

    @Test
    public void whenSetMovieId_callGetPreviewOfPresenter_success() {
        mReviewsMvpView.setMovieId(MOVIE_ID, true);
        verify(mReviewsMvpPresenter).getReviewsPreviews(MOVIE_ID);
    }

    @Test
    public void whenSetMovieId_callGetNextReviewsOfPresenter_success() {
        mReviewsMvpView.setMovieId(MOVIE_ID, false);
        verify(mReviewsMvpPresenter).getNextReviews(MOVIE_ID);
    }

}
