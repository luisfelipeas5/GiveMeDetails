package br.com.luisfelipeas5.givemedetails.presenter.details;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import br.com.luisfelipeas5.givemedetails.model.model.reviews.Review;
import br.com.luisfelipeas5.givemedetails.presenter.schedulers.TestSchedulerProvider;
import br.com.luisfelipeas5.givemedetails.view.details.ReviewsMvpView;
import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReviewsPresenterTest {

    private ReviewsMvpPresenter mReviewsMvpPresenter;
    private TestScheduler mTestScheduler;
    private List<List<Review>> mReviewsPages;

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
        when(mMovieMvpDataManager.getMovieReviewsByPage(mMovie.getId(), 0)).thenReturn(Single.just(mReviews));
        when(mMovieMvpDataManager.getMovieReviewsByPage(mMovie.getId(), 1)).thenReturn(Single.just(mReviewsSecondPage));

        mReviewsPages = new LinkedList<>();
        mReviewsPages.add(mReviews);
        mReviewsPages.add(mReviewsSecondPage);

        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);
        mReviewsMvpPresenter = new ReviewsPresenter(testSchedulerProvider, mMovieMvpDataManager);
        mReviewsMvpPresenter.attach(mReviewsMvpView);
    }

    @Test
    public void whenGetNextReviewsManyTimes_callReviewsReadyFromView_success() {
        int times = 2;
        for (int i = 0; i < times; i++) {
            mReviewsMvpPresenter.getNextReviews(mMovie.getId());
            mTestScheduler.triggerActions();

            verify(mReviewsMvpView).onReviewsReady(mReviewsPages.get(i));
        }
        verify(mReviewsMvpView, never()).onGetReviewsFailed();
    }

    @Test
    public void whenGetNextReviewsManyTimes_callOnFailedFromView_success() {
        when(mMovieMvpDataManager.getMovieReviewsByPage(anyString(), anyInt()))
                .thenReturn(Single.<List<Review>>error(new Exception("Mocked exception")));

        int times = 2;
        for (int i = 0; i < times; i++) {
            mReviewsMvpPresenter.getNextReviews(mMovie.getId());
            mTestScheduler.triggerActions();
            verify(mReviewsMvpView, Mockito.times(i + 1)).onGetReviewsFailed();
        }
        verify(mReviewsMvpView, never()).onReviewsReady(anyListOf(Review.class));
    }

    @Test
    public void whenGetNextReviews_dataManagerReturnError_currentPageDoesNotChange_success() {
        when(mMovieMvpDataManager.getMovieReviewsByPage(anyString(), anyInt()))
                .thenReturn(Single.<List<Review>>error(new Exception("Mocked exception")));

        mReviewsMvpPresenter.getNextReviews(mMovie.getId());
        int pageExpected = 0;
        Assert.assertEquals(pageExpected, mReviewsMvpPresenter.getCurrentPage());
        mTestScheduler.triggerActions();
        Assert.assertEquals(pageExpected, mReviewsMvpPresenter.getCurrentPage());
    }

    @Test
    public void whenGetNextReviewsManyTimes_callDataManager_success() {
        int times = 2;
        for (int i = 0; i < times; i++) {
            mReviewsMvpPresenter.getNextReviews(mMovie.getId());
            mTestScheduler.triggerActions();
            verify(mMovieMvpDataManager).getMovieReviewsByPage(mMovie.getId(), i);
        }
    }

    @Test
    public void whenGetNextReviews_callRefreshingFromView_success() {
        mReviewsMvpPresenter.getNextReviews(mMovie.getId());
        verify(mReviewsMvpView).onGettingReviews(true);
        mTestScheduler.triggerActions();
        verify(mReviewsMvpView).onGettingReviews(false);
    }

    @Test
    public void whenGetNextReviews_twiceWithoutTrigger_callOnceDataManager_success() {
        mReviewsMvpPresenter.getNextReviews(mMovie.getId());
        mReviewsMvpPresenter.getNextReviews(mMovie.getId());
        mTestScheduler.triggerActions();

        verify(mMovieMvpDataManager).getMovieReviewsByPage(eq(mMovie.getId()), anyInt());

        mReviewsMvpPresenter.getNextReviews(mMovie.getId());
        mTestScheduler.triggerActions();
        verify(mMovieMvpDataManager).getMovieReviewsByPage(mMovie.getId(), 1);
    }

    @Test
    public void whenGetCurrentPage_beforeGetNextReviews_returnCurrentPage_success() {
        int times = 2;
        for (int i = 0; i < times; i++) {
            Assert.assertEquals(i, mReviewsMvpPresenter.getCurrentPage());
            mReviewsMvpPresenter.getNextReviews(mMovie.getId());
            mTestScheduler.triggerActions();
        }
    }

    @Test
    public void whenGetCurrentPage_afterGetNextReviews_returnCurrentPage_success() {
        int times = 2;
        for (int i = 0; i < times; i++) {
            mReviewsMvpPresenter.getNextReviews(mMovie.getId());
            mTestScheduler.triggerActions();
            Assert.assertEquals(i + 1, mReviewsMvpPresenter.getCurrentPage());
        }
    }

}
