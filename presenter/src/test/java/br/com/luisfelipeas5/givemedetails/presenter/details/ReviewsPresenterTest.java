package br.com.luisfelipeas5.givemedetails.presenter.details;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import br.com.luisfelipeas5.givemedetails.model.model.reviews.Review;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.TestSchedulerProvider;
import br.com.luisfelipeas5.givemedetails.view.details.ReviewsMvpView;
import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReviewsPresenterTest {

    private ReviewsMvpPresenter mReviewsMvpPresenter;
    private TestScheduler mTestScheduler;

    @Mock
    private MovieMvpDataManager mMovieMvpDataManager;
    @Mock
    private ReviewsMvpView mReviewsMvpView;
    @Mock
    private Movie mMovie;
    @Mock
    private List<Review> mReviews;
    @Mock
    private List<Review> mReviewsSecondPage;

    @Before

    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(mMovie.getId()).thenReturn("Movie Id Mocked");
        when(mMovieMvpDataManager.getMovieReviews(mMovie.getId())).thenReturn(Single.just(mReviews));
        when(mMovieMvpDataManager.getMovieReviewsByPage(mMovie.getId(), 1)).thenReturn(Single.just(mReviewsSecondPage));

        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);
        mReviewsMvpPresenter = new ReviewsPresenter(testSchedulerProvider);
    }

    @Test
    public void whenGetNextReviews_callDataManagerAndCallView_success() {
        mReviewsMvpPresenter.getNextReviews(mMovie.getId());
        mTestScheduler.triggerActions();

        verify(mMovieMvpDataManager).getMovieReviews(mMovie.getId());
        verify(mReviewsMvpView).onReviewsReady(mReviews);
        verify(mReviewsMvpView, never()).onGetReviewsFailed();
    }

    @Test
    public void whenGetNextReviewsTwice_callDataManagerAndCallView_success() {
        mReviewsMvpPresenter.getNextReviews(mMovie.getId());
        mTestScheduler.triggerActions();
        mReviewsMvpPresenter.getNextReviews(mMovie.getId());
        mTestScheduler.triggerActions();

        verify(mMovieMvpDataManager).getMovieReviews(mMovie.getId());
        verify(mMovieMvpDataManager).getMovieReviewsByPage(mMovie.getId(), 1);
        verify(mReviewsMvpView).onReviewsReady(mReviews);
        verify(mReviewsMvpView).onReviewsReady(mReviewsSecondPage);
        verify(mReviewsMvpView, never()).onGetReviewsFailed();
    }

    @Test
    public void whenGetCurrentPage_returnCurrentPage_success() {
        Assert.assertEquals(0, mReviewsMvpPresenter.getCurrentPage());
        for (int i = 0; i < 10; i++) {
            mReviewsMvpPresenter.getNextReviews(mMovie.getId());
            mTestScheduler.triggerActions();
            Assert.assertEquals(i + 1, mReviewsMvpPresenter.getCurrentPage());
        }
    }

}
