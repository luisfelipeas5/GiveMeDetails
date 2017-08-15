package br.com.luisfelipeas5.givemedetails.model.datamanagers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieDataManager;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieApiMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieCacheMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import br.com.luisfelipeas5.givemedetails.model.model.reviews.Review;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReviewsDataManagerTest {

    private MovieDataManager mDataManager;
    @Mock
    private MovieApiMvpHelper mApiMvpHelper;
    @Mock
    private MovieCacheMvpHelper mCacheMvpHelper;
    @Mock
    private Movie mMovie;
    @Mock
    private List<Review> mReviews;

    @Mock private List<Review> mReviewsSecondPage;
    @Mock private List<Review> mReviewsThirdPage;
    @Mock private List<Review> mReviewsForthPage;

    private List<List<Review>> mReviewsPages;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(mMovie.getId()).thenReturn("Movie Id Mocked");
        when(mApiMvpHelper.getReviews(mMovie.getId(), 0)).thenReturn(Observable.just(mReviews));
        when(mApiMvpHelper.getReviews(mMovie.getId(), 1)).thenReturn(Observable.just(mReviewsSecondPage));
        when(mApiMvpHelper.getReviews(mMovie.getId(), 2)).thenReturn(Observable.just(mReviewsThirdPage));
        when(mApiMvpHelper.getReviews(mMovie.getId(), 3)).thenReturn(Observable.just(mReviewsForthPage));

        mReviewsPages = new ArrayList<>(4);
        mReviewsPages.add(mReviews);
        mReviewsPages.add(mReviewsSecondPage);
        mReviewsPages.add(mReviewsThirdPage);
        mReviewsPages.add(mReviewsForthPage);

        mDataManager = new MovieDataManager(mApiMvpHelper, mCacheMvpHelper);
    }

    @Test
    public void whenGetReviewsByPage_returnThatPage_success() {
        for (int i = 0; i < mReviewsPages.size(); i++) {
            List<Review> reviews = mReviewsPages.get(i);

            String movieId = mMovie.getId();
            Single<List<Review>> reviewsSingle = mDataManager.getMovieReviewsByPage(movieId, i);
            TestObserver<List<Review>> testObserver = reviewsSingle.test();
            testObserver.assertComplete();
            testObserver.assertNoErrors();
            testObserver.assertValue(reviews);

            verify(mApiMvpHelper).getReviews(movieId, i);
        }
    }

}
