package br.com.luisfelipeas5.gimmedetails.ui.medias

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import br.com.luisfelipeas5.gimmedetails.R
import br.com.luisfelipeas5.gimmedetails.model.medias.Media
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class ListOfMediaActivityTest {

    @get:Rule
    val mActivityRule: ActivityTestRule<MediasActivity>
            = ActivityTestRule<MediasActivity>(MediasActivity::class.java)

    private val mMedias: List<Media>? = null

    @Test
    fun appearsList() {
        onView(withId(R.id.rv_medias))
                .check(matches(isDisplayed()))
    }

    @Test
    fun appearsMediasTitle() {
        Assert.assertNotNull(mMedias)
        if (mMedias != null) {
            for (media in mMedias) {
                val title = media.title
                onView(withId(R.id.rv_medias))
                        .perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                                hasDescendant(withText(title))
                        ))
            }
        }
    }

}