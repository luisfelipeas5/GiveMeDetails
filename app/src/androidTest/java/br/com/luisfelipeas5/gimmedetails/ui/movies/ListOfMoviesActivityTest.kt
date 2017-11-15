package br.com.luisfelipeas5.gimmedetails.ui.movies

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import br.com.luisfelipeas5.gimmedetails.R
import org.junit.Rule
import org.junit.Test

class ListOfMoviesActivityTest {

    @get:Rule
    val mActivityRule: ActivityTestRule<MoviesActivity>
            = ActivityTestRule<MoviesActivity>(MoviesActivity::class.java)

    @Test
    fun appearsList() {
        onView(withId(R.id.rv_movies))
                .check(matches(isDisplayed()))
    }

}