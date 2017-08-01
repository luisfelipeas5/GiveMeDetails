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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.com.luisfelipeas5.givemedetails.R;
import br.com.luisfelipeas5.givemedetails.model.model.Movie;
import br.com.luisfelipeas5.givemedetails.rules.AppTestComponentTestRule;
import br.com.luisfelipeas5.givemedetails.view.activities.DetailActivity;
import br.com.luisfelipeas5.givemedetails.view.di.modules.model.ModelTestModule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class SummaryInstrumentedTest {

    @Rule
    public ActivityTestRule<DetailActivity> mActivityRule = new ActivityTestRule<>(DetailActivity.class, true, false);

    @Rule
    public AppTestComponentTestRule appTestComponentTestRule = new AppTestComponentTestRule();

    private Context mContext;
    private Movie mMovie;

    @Before
    public void setUp() {
        mContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        mMovie = ModelTestModule.getMovieMocked();

        Intent intent = new Intent(mContext, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, mMovie.getId());
        mActivityRule.launchActivity(intent);
    }

    @Test
    public void whenLoadSummary_appearTitle_success() {
        String title = mMovie.getTitle();
        onView(withId(R.id.txt_title)).check(getTextMatcher(title));
    }

    @Test
    public void whenLoadSummary_appearOriginalTitle_success() {
        String originalTitle = mContext.getString(R.string.original_title, mMovie.getOriginalTitle());
        onView(withId(R.id.txt_original_title))
                .check(getTextMatcher(originalTitle));
    }

    @Test
    public void whenLoadSummary_appearOverview_success() {
        String overview = mMovie.getOverview();
        onView(withId(R.id.txt_overview)).check(getTextMatcher(overview));
    }

    @Test
    public void whenLoadSummary_appearReleaseDate_success() {
        String pattern = mContext.getString(R.string.date_format_pattern);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());

        Date releaseDateAsDate = mMovie.getReleaseDateAsDate();
        String releaseDateFormatted = mContext.getString(R.string.release_date, simpleDateFormat.format(releaseDateAsDate));

        onView(withId(R.id.txt_release_date))
                .check(getTextMatcher(releaseDateFormatted));
    }

    @NonNull
    private ViewAssertion getTextMatcher(String text) {
        return matches(withText(text));
    }

}
