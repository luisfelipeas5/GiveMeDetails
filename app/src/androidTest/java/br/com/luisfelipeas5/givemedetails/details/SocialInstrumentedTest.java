package br.com.luisfelipeas5.givemedetails.details;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewAssertion;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.luisfelipeas5.givemedetails.R;
import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import br.com.luisfelipeas5.givemedetails.rules.AppTestComponentTestRule;
import br.com.luisfelipeas5.givemedetails.view.activities.DetailActivity;
import br.com.luisfelipeas5.givemedetails.view.di.modules.model.ModelTestModule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class SocialInstrumentedTest {

    @Rule
    public ActivityTestRule<DetailActivity> mActivityRule = new ActivityTestRule<>(DetailActivity.class, true, false);

    @Rule
    public AppTestComponentTestRule appTestComponentTestRule = new AppTestComponentTestRule();

    private Movie mMovie;
    private Context mContext;

    @Before
    public void setUp() {
        mContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        mMovie = ModelTestModule.getMovieMocked();

        Intent intent = new Intent(mContext, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, mMovie.getId());
        mActivityRule.launchActivity(intent);
    }

    @Test
    public void whenLoadSocial_appearVoteAverage_success() {
        String voteAverageAsString = Double.toString(mMovie.getVoteAverage());
        onView(withId(R.id.txt_vote_average)).check(getTextMatcher(voteAverageAsString));
    }

    @Test
    public void whenLoadSocial_appearVoteCount_success() {
        long voteCount = mMovie.getVoteCount();
        String voteCountWithLabel = mContext.getString(R.string.vote_count, voteCount);
        onView(withId(R.id.txt_vote_count)).check(getTextMatcher(voteCountWithLabel));
    }

    @NonNull
    private ViewAssertion getTextMatcher(String text) {
        return matches(withText(text));
    }

}
