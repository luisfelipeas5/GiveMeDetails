package br.com.luisfelipeas5.givemedetails.details;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.LinkedList;
import java.util.List;

import br.com.luisfelipeas5.givemedetails.R;
import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import br.com.luisfelipeas5.givemedetails.model.model.trailer.Trailer;
import br.com.luisfelipeas5.givemedetails.rules.AppTestComponentTestRule;
import br.com.luisfelipeas5.givemedetails.view.activities.DetailActivity;
import br.com.luisfelipeas5.givemedetails.view.di.modules.model.ModelTestModule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class TrailersInstrumentedTest {

    @Rule
    public ActivityTestRule<DetailActivity> mActivityRule = new ActivityTestRule<>(DetailActivity.class, true, false);
    @Rule
    public AppTestComponentTestRule appTestComponentTestRule = new AppTestComponentTestRule();

    private List<Trailer> mTrailers;
    private Intent intent;

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Movie movie = ModelTestModule.getMovieMocked();
        mTrailers = ModelTestModule.getTrailersMocked();

        intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, movie.getId());
    }

    @Test
    public void whenLoadTrailers_showNameForEachTrailer_success() {
        mActivityRule.launchActivity(intent);

        onView(allOf(withId(R.id.progress_bar), isDescendantOfA(withId(R.id.fragment_trailers))))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(withId(R.id.txt_no_trailers))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        for (Trailer trailer : mTrailers) {
            onView(withText(trailer.getName()))
                    .perform(ViewActions.scrollTo())
                    .check(
                            matches(allOf(withId(R.id.txt_trailer_name), withText(trailer.getName())))
                    );

        }
    }

    @Test
    public void whenLoadNoTrailers_showMessage_success() {
        ModelTestModule modelTestModule = new ModelTestModule();
        modelTestModule.setTrailers(new LinkedList<Trailer>());
        AppTestComponentTestRule.setAppTestComponent(modelTestModule);
        mActivityRule.launchActivity(intent);

        onView(allOf(withId(R.id.progress_bar), isDescendantOfA(withId(R.id.fragment_trailers))))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(withId(R.id.recycler_trailers))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(withId(R.id.txt_no_trailers))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
                .check(matches(withText(R.string.no_trailers)));
    }

    @Test
    public void whenLoadTrailersError_showMessage_success() {
        ModelTestModule modelTestModule = new ModelTestModule();
        modelTestModule.setTrailers(null);
        AppTestComponentTestRule.setAppTestComponent(modelTestModule);
        mActivityRule.launchActivity(intent);

        onView(allOf(withId(R.id.progress_bar), isDescendantOfA(withId(R.id.fragment_trailers))))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(withId(R.id.recycler_trailers))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(withId(R.id.txt_no_trailers))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
                .check(matches(withText(R.string.no_trailers)));
    }

}
