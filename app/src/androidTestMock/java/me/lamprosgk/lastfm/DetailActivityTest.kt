package me.lamprosgk.lastfm

import android.app.Activity
import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasAction
import android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.*
import android.widget.TextView
import me.lamprosgk.lastfm.data.remote.MockHelper
import me.lamprosgk.lastfm.ui.detail.DetailActivity
import me.lamprosgk.lastfm.ui.search.adapter.ArtistsAdapter
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.Matchers.equalToIgnoringCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class DetailActivityTest {

    @get:Rule
    val intentsRule = IntentsTestRule<DetailActivity>(DetailActivity::class.java, true, false)
    private val artist = MockHelper.artistsResponse.results.artistMatches.artist[0]
    private val tracks = MockHelper.tracksResponse.topTracks.track
    private lateinit var activity: Activity

    @Before
    fun setUp() {
        // launch activity with artist from static json
        val i = Intent()
        i.putExtra(DetailActivity.EXTRA_ARTIST, artist)
        intentsRule.launchActivity(i)
        activity = intentsRule.activity

    }


    @Test
    fun testAllViewsAppearOnScreenAndShowCorrectData() {

        // toolbar title
        onView(
            allOf(
                instanceOf(TextView::class.java),
                withParent(withId(R.id.toolbar))
            )
        )
            .check(matches(withText("Cher")))

        // backdrop
        onView(withId(R.id.artistImageDetail)).check(matches(isDisplayed()))

        // listeners
        onView(withId(R.id.detailListenersTextView)).check(matches(isDisplayed()))
        val listeners = activity.getString(R.string.detail_listeners_template, artist.listeners)
        onView(withId(R.id.detailListenersTextView)).check(matches(withText(listeners)))

        // fab
        onView(withId(R.id.fab)).check(matches(isDisplayed()))

        // tracks label
        onView(withText(equalToIgnoringCase("top tracks"))).check(matches(isDisplayed()))

        // recyclerView
        val position = 4
        val trackTitle = tracks[position].name
        onView(withId(R.id.tracksRecyclerView)).check(matches(isDisplayed()))
        // shows correct track
        onView(withId(R.id.tracksRecyclerView))
            .perform(RecyclerViewActions.scrollToPosition<ArtistsAdapter.ViewHolder>(position))
        onView(withSubstring(trackTitle)).check(matches(isDisplayed()))

    }

    @Test
    fun testFabClickIsSharing() {
        // test that clicking on fab is creating and sending a share intent with correct data

        val shareText = activity.getString(R.string.share_text, artist.url)

        onView(withId(R.id.fab)).perform(click())

        intended(
            allOf(
                hasAction(Intent.ACTION_SEND),
                (hasExtra(Intent.EXTRA_TEXT, shareText))
            )
        )
    }


}