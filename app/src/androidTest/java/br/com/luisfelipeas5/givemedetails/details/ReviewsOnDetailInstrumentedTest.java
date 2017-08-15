package br.com.luisfelipeas5.givemedetails.details;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import br.com.luisfelipeas5.givemedetails.R;
import br.com.luisfelipeas5.givemedetails.model.model.movie.Movie;
import br.com.luisfelipeas5.givemedetails.model.model.reviews.Review;
import br.com.luisfelipeas5.givemedetails.rules.AppTestComponentTestRule;
import br.com.luisfelipeas5.givemedetails.view.activities.DetailActivity;
import br.com.luisfelipeas5.givemedetails.view.di.modules.model.ModelTestModule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNot.not;

public class ReviewsOnDetailInstrumentedTest {

    private static final int REVIEWS_COUNT_MAX = 3;

    @Rule
    public ActivityTestRule<DetailActivity> mActivityRule = new ActivityTestRule<>(DetailActivity.class, true, false);
    @Rule
    public AppTestComponentTestRule appTestComponentTestRule = new AppTestComponentTestRule();

    private Intent intent;
    private List<Review> mReviews;

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Movie movie = ModelTestModule.getMovieMocked();
        mReviews = ModelTestModule.getReviewsMocked();

        intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, movie.getId());
    }

    @Test
    public void whenGetReviews_showPreviewOfReviews_success() {
        mActivityRule.launchActivity(intent);

        onView(allOf(withId(R.id.progress_bar), isDescendantOfA(withId(R.id.fragment_reviews_preview))))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(allOf(withId(R.id.txt_no_reviews), isDescendantOfA(withId(R.id.fragment_reviews_preview))))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        for (int i = 0; i < REVIEWS_COUNT_MAX; i++) {
            Review review = mReviews.get(i);
            onView(allOf(withText(review.getAuthor()), isDescendantOfA(withId(R.id.fragment_reviews_preview))))
                    .perform(ViewActions.scrollTo())
                    .check(matches(allOf(withId(R.id.txt_author), withText(review.getAuthor()))));

            onView(allOf(withText(review.getContent()), isDescendantOfA(withId(R.id.fragment_reviews_preview))))
                    .perform(ViewActions.scrollTo())
                    .check(matches(allOf(withId(R.id.txt_content), withText(review.getContent()))));
        }

        for (int i = REVIEWS_COUNT_MAX; i < mReviews.size(); i++) {
            Review review = mReviews.get(i);
            onView(allOf(withText(review.getAuthor()), isDescendantOfA(withId(R.id.fragment_reviews_preview))))
                    .check(doesNotExist());

            onView(allOf(withText(review.getContent()), isDescendantOfA(withId(R.id.fragment_reviews_preview))))
                    .check(doesNotExist());
        }

        onView(allOf(withId(R.id.button_see_all_reviews), isDescendantOfA(withId(R.id.fragment_reviews_preview))))
                .check(
                        matches(allOf(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE), withText(R.string.see_all_reviews)))
                );
    }

    @Test
    public void whenGetReviews_withShortList_showPreviewOfReviews_success() {
        ModelTestModule modelTestModule = new ModelTestModule();
        List<Review> reviews = mReviews.subList(0, 3);
        modelTestModule.setReviews(reviews);
        AppTestComponentTestRule.setAppTestComponent(modelTestModule);
        mActivityRule.launchActivity(intent);

        onView(allOf(withId(R.id.progress_bar), isDescendantOfA(withId(R.id.fragment_reviews_preview))))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(allOf(withId(R.id.txt_no_reviews), isDescendantOfA(withId(R.id.fragment_reviews_preview))))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        for (Review review: reviews) {
            onView(allOf(withText(review.getAuthor()), isDescendantOfA(withId(R.id.fragment_reviews_preview))))
                    .perform(ViewActions.scrollTo())
                    .check(matches(allOf(withId(R.id.txt_author), withText(review.getAuthor()))));

            onView(allOf(withText(review.getContent()), isDescendantOfA(withId(R.id.fragment_reviews_preview))))
                    .perform(ViewActions.scrollTo())
                    .check(matches(allOf(withId(R.id.txt_content), withText(review.getContent()))));
        }

        onView(allOf(withId(R.id.button_see_all_reviews), isDescendantOfA(withId(R.id.fragment_reviews_preview))))
                .check(
                        matches(allOf(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE), withText(R.string.see_all_reviews)))
                );
    }

    @Test
    public void whenGetReviewsReturnEmpty_showMessage_success() {
        ModelTestModule modelTestModule = new ModelTestModule();
        modelTestModule.setReviews(new LinkedList<Review>());
        AppTestComponentTestRule.setAppTestComponent(modelTestModule);
        mActivityRule.launchActivity(intent);

        onView(allOf(withId(R.id.progress_bar), isDescendantOfA(withId(R.id.fragment_reviews_preview))))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(allOf(withId(R.id.txt_no_reviews), isDescendantOfA(withId(R.id.fragment_reviews_preview))))
                .check(matches(withText(R.string.no_reviews)))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void whenGetReviewsReturnError_showMessage_success() {
        ModelTestModule modelTestModule = new ModelTestModule();
        modelTestModule.setReviews(null);
        AppTestComponentTestRule.setAppTestComponent(modelTestModule);
        mActivityRule.launchActivity(intent);

        onView(allOf(withId(R.id.progress_bar), isDescendantOfA(withId(R.id.fragment_reviews_preview))))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(allOf(withId(R.id.txt_no_reviews), isDescendantOfA(withId(R.id.fragment_reviews_preview))))
                .check(matches(withText(R.string.no_reviews)))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void whenGetReviews_showAllReviews_success() {
        mActivityRule.launchActivity(intent);

        onView(allOf(withId(R.id.button_see_all_reviews), isDescendantOfA(withId(R.id.fragment_reviews_preview))))
                .perform(ViewActions.scrollTo())
                .perform(ViewActions.click());

        onView(allOf(withId(R.id.progress_bar), isDescendantOfA(withId(R.id.fragment_reviews))))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(allOf(withId(R.id.txt_no_reviews), isDescendantOfA(withId(R.id.fragment_reviews))))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        for (int i = 0; i < mReviews.size(); i++) {
            Review review = mReviews.get(i);

            onView(allOf(withText(review.getAuthor()), isDescendantOfA(withId(R.id.fragment_reviews))))
                    .perform(ViewActions.scrollTo())
                    .check(matches(allOf(withId(R.id.txt_author), withText(review.getAuthor()))));

            onView(allOf(withText(review.getContent()), isDescendantOfA(withId(R.id.fragment_reviews))))
                    .perform(ViewActions.scrollTo())
                    .check(matches(allOf(withId(R.id.txt_content), withText(review.getContent()))));
        }

        onView(allOf(withId(R.id.button_see_all_reviews), isDescendantOfA(withId(R.id.fragment_reviews))))
                .check(matches(not(isDisplayed())));
    }

}
