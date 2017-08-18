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

import br.com.luisfelipeas5.givemedetails.R;
import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import br.com.luisfelipeas5.givemedetails.model.model.movie.MovieLove;
import br.com.luisfelipeas5.givemedetails.rules.AppTestComponentTestRule;
import br.com.luisfelipeas5.givemedetails.view.activities.DetailActivity;
import br.com.luisfelipeas5.givemedetails.view.di.modules.model.ModelTestModule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LoveOnDetailInstrumentedTest {

    @Rule
    public ActivityTestRule<DetailActivity> mActivityRule = new ActivityTestRule<>(DetailActivity.class, true, false);

    @Rule
    public AppTestComponentTestRule appTestComponentTestRule = new AppTestComponentTestRule();

    private MovieLove mMovieLove;
    private Intent intent;

    @Before
    public void setUp() {
        Context mContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Movie mMovie = ModelTestModule.getMovieMocked();
        mMovieLove = ModelTestModule.getMovieLoveMocked(mMovie.getId());

        intent = new Intent(mContext, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, mMovie.getId());
    }

    @Test
    public void whenMovieIsLoved_buttonLoveIsTrue_success() {
        mMovieLove.setLoved(true);
        mActivityRule.launchActivity(intent);

        onView(withId(R.id.button_love))
                .check(matches(
                        withContentDescription(Boolean.toString(mMovieLove.isLoved()))
                ));
    }

    @Test
    public void whenMovieIsNotLoved_buttonLoveIsFalse_success() {
        mMovieLove.setLoved(false);
        mActivityRule.launchActivity(intent);

        onView(withId(R.id.button_love))
                .check(matches(
                        withContentDescription(Boolean.toString(mMovieLove.isLoved()))
                ));
    }

}
