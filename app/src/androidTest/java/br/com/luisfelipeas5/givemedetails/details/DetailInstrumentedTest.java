package br.com.luisfelipeas5.givemedetails.details;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import br.com.luisfelipeas5.givemedetails.R;
import br.com.luisfelipeas5.givemedetails.details.di.MoviesDaggerMockRule;
import br.com.luisfelipeas5.givemedetails.model.helpers.MovieApiMvpHelper;
import br.com.luisfelipeas5.givemedetails.model.model.Movie;
import br.com.luisfelipeas5.givemedetails.model.model.MovieTMDb;
import br.com.luisfelipeas5.givemedetails.view.activities.DetailActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class DetailInstrumentedTest {

    @Rule
    public MoviesDaggerMockRule moviesDaggerMockRule = new MoviesDaggerMockRule();

    @Rule
    public ActivityTestRule<DetailActivity> mActivityRule = new ActivityTestRule<>(DetailActivity.class, true, false);

    @Mock
    public MovieApiMvpHelper mMovieApiMvpHelper;

    private Context mContext;
    private Movie mMovie;

    @Before
    public void setUp() {
        mMovie = new MovieTMDb();
        mMovie.setId("119450");
        mMovie.setTitle("Dawn of the Planet of the Apes");

        mContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(mContext, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, mMovie.getId());
        mActivityRule.launchActivity(intent);
    }

    @Test
    public void whenActivityLaunched_loadPoster_appearPoster() {
        String movieTitleExpected = mContext.getString(R.string.movie_poster_description, mMovie.getTitle());
        onView(withId(R.id.img_movie_poster))
                .check(matches(withContentDescription(movieTitleExpected)));
    }

    @Test
    public void whenActivityLaunched_loadTitle_appearTitle() {
        String title = mMovie.getTitle();
        onView(withId(R.id.txt_title)).check(matches(withText(title)));
    }

}
