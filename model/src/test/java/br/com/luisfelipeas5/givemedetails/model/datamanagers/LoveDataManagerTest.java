package br.com.luisfelipeas5.givemedetails.model.datamanagers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieDataManager;
import br.com.luisfelipeas5.givemedetails.model.datamangers.MovieMvpDataManager;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieApiMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieCacheMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.helpers.DatabaseMvpHelper;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoveDataManagerTest {

    private static final String MOVIE_ID_MOCKED = "Movie id mocked";
    private MovieMvpDataManager mMovieMvpDataManager;

    @Mock
    private MovieApiMvpHelper mApiMvpHelper;
    @Mock
    private MovieCacheMvpHelper mCacheMvpHelper;
    @Mock
    private DatabaseMvpHelper mDatabaseMvpHelper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(mDatabaseMvpHelper.isLoved(MOVIE_ID_MOCKED)).thenReturn(Single.just(false));
        when(mDatabaseMvpHelper.setIsLoved(MOVIE_ID_MOCKED, true)).thenReturn(Completable.complete());
        when(mDatabaseMvpHelper.setIsLoved(MOVIE_ID_MOCKED, false)).thenReturn(Completable.complete());

        mMovieMvpDataManager = new MovieDataManager(mApiMvpHelper, mCacheMvpHelper, mDatabaseMvpHelper);
    }

    @Test
    public void whenToggleMovieLove_singleCompleteAndNoError_success() {
        Single<Boolean> toggleLoveSingle = mMovieMvpDataManager.toggleMovieLove(MOVIE_ID_MOCKED);
        TestObserver<Boolean> testObserver = toggleLoveSingle.test();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
    }

    @Test
    public void whenToggleMovieLove_callIsLovedOfDatabaseHelper_success() {
        Single<Boolean> toggleLoveSingle = mMovieMvpDataManager.toggleMovieLove(MOVIE_ID_MOCKED);
        toggleLoveSingle.test();
        verify(mDatabaseMvpHelper).isLoved(MOVIE_ID_MOCKED);
    }

    @Test
    public void whenToggleMovieLove_callSetLoveOfDatabaseHelper_withFalse_success() {
        when(mDatabaseMvpHelper.isLoved(MOVIE_ID_MOCKED)).thenReturn(Single.just(true));

        Single<Boolean> toggleLoveSingle = mMovieMvpDataManager.toggleMovieLove(MOVIE_ID_MOCKED);
        toggleLoveSingle.test();

        verify(mDatabaseMvpHelper).setIsLoved(MOVIE_ID_MOCKED, false);
    }

    @Test
    public void whenToggleMovieLove_callSetLoveOfDatabaseHelper_withTrue_success() {
        when(mDatabaseMvpHelper.isLoved(MOVIE_ID_MOCKED)).thenReturn(Single.just(false));

        Single<Boolean> toggleLoveSingle = mMovieMvpDataManager.toggleMovieLove(MOVIE_ID_MOCKED);
        toggleLoveSingle.test();

        verify(mDatabaseMvpHelper).setIsLoved(MOVIE_ID_MOCKED, true);
    }

    @Test
    public void whenToggleMovieLove_returnTrue_success() {
        when(mDatabaseMvpHelper.isLoved(MOVIE_ID_MOCKED)).thenReturn(Single.just(false));

        Single<Boolean> toggleLoveSingle = mMovieMvpDataManager.toggleMovieLove(MOVIE_ID_MOCKED);
        TestObserver<Boolean> testObserver = toggleLoveSingle.test();
        testObserver.assertValue(true);
    }

    @Test
    public void whenToggleMovieLove_returnFalse_success() {
        when(mDatabaseMvpHelper.isLoved(MOVIE_ID_MOCKED)).thenReturn(Single.just(true));

        Single<Boolean> toggleLoveSingle = mMovieMvpDataManager.toggleMovieLove(MOVIE_ID_MOCKED);
        TestObserver<Boolean> testObserver = toggleLoveSingle.test();
        testObserver.assertValue(false);
    }

}
