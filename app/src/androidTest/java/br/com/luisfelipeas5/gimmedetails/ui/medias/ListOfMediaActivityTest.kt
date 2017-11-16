package br.com.luisfelipeas5.gimmedetails.ui.medias

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import android.view.View
import br.com.luisfelipeas5.gimmedetails.R
import br.com.luisfelipeas5.gimmedetails.model.medias.Media
import org.hamcrest.Matcher
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class ListOfMediaActivityTest {

    @Rule
    @JvmField
    val mActivityRule = ActivityTestRule<MediasActivity>(MediasActivity::class.java)

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
                assertTextInMediaList(title)
            }
        }
    }

    @Test
    fun appearsMediasType() {
        Assert.assertNotNull(mMedias)
        if (mMedias != null) {
            for (media in mMedias) {
                val type = media.type
                assertTextInMediaList(type)
            }
        }
    }

    @Test
    fun appearsMediasImage() {
        Assert.assertNotNull(mMedias)
        val context = InstrumentationRegistry.getTargetContext()
        if (mMedias != null) {
            for (media in mMedias) {
                val title = media.title
                val image = media.image
                val contentDescription = context.getString(R.string.media_image, title, image)
                assertInMediaList(withContentDescription(contentDescription))
            }
        }
    }

    private fun assertTextInMediaList(text: String) {
        val withText = withText(text)
        assertInMediaList(withText)
    }

    private fun assertInMediaList(withText: Matcher<View>?) {
        onView(withId(R.id.rv_medias))
                .perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                        hasDescendant(withText)
                ))
    }

}