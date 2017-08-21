package br.com.luisfelipeas5.givemedetails.details;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
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

    private Intent intent;

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Movie mMovie = ModelTestModule.getMovieMocked();

        intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, mMovie.getId());
    }

    @Test
    public void whenMovieIsLoved_buttonLoveIsTrue_success() {
        ModelTestModule modelTestModule = new ModelTestModule();
        MovieLove movieLove = new MovieLove();
        movieLove.setLoved(true);
        modelTestModule.setMovieLove(movieLove);
        AppTestComponentTestRule.setAppTestComponent(modelTestModule);
        mActivityRule.launchActivity(intent);

        onView(withId(R.id.button_love))
                .check(matches(withContentDescription(R.string.movie_was_loved_by_you)));
    }

    @Test
    public void whenMovieIsNotLoved_buttonLoveIsFalse_success() {
        ModelTestModule modelTestModule = new ModelTestModule();
        MovieLove movieLove = new MovieLove();
        movieLove.setLoved(false);
        modelTestModule.setMovieLove(movieLove);
        AppTestComponentTestRule.setAppTestComponent(modelTestModule);
        mActivityRule.launchActivity(intent);

        onView(withId(R.id.button_love))
                .check(matches(withContentDescription(R.string.movie_was_not_loved_by_you)));
    }

    @Test
    public void whenMovieIsNotLoved_andLoveButtonIsClicked_buttonTurnsTrue_success() {
        ModelTestModule modelTestModule = new ModelTestModule();
        MovieLove movieLove = new MovieLove();
        movieLove.setLoved(false);
        modelTestModule.setMovieLove(movieLove);
        AppTestComponentTestRule.setAppTestComponent(modelTestModule);
        mActivityRule.launchActivity(intent);

        ViewInteraction buttonLoveViewInteraction = onView(withId(R.id.button_love));
        buttonLoveViewInteraction.perform(ViewActions.click());
        buttonLoveViewInteraction.check(matches(withContentDescription(R.string.movie_was_loved_by_you)));
    }

    @Test
    public void whenMovieIsLoved_andLoveButtonIsClicked_buttonTurnsFalse_success() {
        ModelTestModule modelTestModule = new ModelTestModule();
        MovieLove movieLove = new MovieLove();
        movieLove.setLoved(true);
        modelTestModule.setMovieLove(movieLove);
        AppTestComponentTestRule.setAppTestComponent(modelTestModule);
        mActivityRule.launchActivity(intent);

        ViewInteraction buttonLoveViewInteraction = onView(withId(R.id.button_love));
        buttonLoveViewInteraction.perform(ViewActions.click());
        buttonLoveViewInteraction.check(matches(withContentDescription(R.string.movie_was_not_loved_by_you)));
    }

}
