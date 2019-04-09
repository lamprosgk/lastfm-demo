package me.lamprosgk.lastfm

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.toPackage
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import me.lamprosgk.lastfm.ui.search.SearchActivity
import me.lamprosgk.lastfm.ui.search.adapter.ArtistsAdapter
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matchers.equalToIgnoringCase
import org.junit.Rule
import org.junit.Test


class SearchActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(SearchActivity::class.java)

    @Test
    fun testAllViewsAppearOnScreen() {

        // empty view message
        onView(withId(R.id.emptyView)).check(matches(isDisplayed()))
        onView(withId(R.id.emptyView)).check(matches(withText(R.string.search_message_empty)))

        // search action view
        onView(withId(R.id.search)).check(matches(isDisplayed()))
        onView(withId(R.id.search)).perform(click())

        // search editText
        onView(withId(android.support.v7.appcompat.R.id.search_src_text)).check(matches(isDisplayed()))
        onView(withId(android.support.v7.appcompat.R.id.search_src_text)).check(matches(withHint(R.string.search_hint)))
    }


    @Test
    fun testCorrectViewsAreVisibleOnSearchResult() {

        onView(withId(R.id.search)).perform(click())
        onView(withId(android.support.v7.appcompat.R.id.search_src_text)).perform(ViewActions.typeText("ch"))

        Thread.sleep(1000)

        // check empty view not not visible & recyclerView visible
        onView(withId(R.id.emptyView)).check(matches(not(isDisplayed())))
        onView(withId(R.id.artistsRecyclerView)).check(matches(isDisplayed()))

    }

    @Test
    fun testRecyclerViewDisplaysCorrectResults() {

        onView(withId(R.id.search)).perform(click())
        onView(withId(android.support.v7.appcompat.R.id.search_src_text)).perform(ViewActions.typeText("Che"))

        Thread.sleep(1000)
        onView(withId(R.id.artistsRecyclerView)).perform(RecyclerViewActions.scrollToPosition<ArtistsAdapter.ViewHolder>(5))
        onView(withText(equalToIgnoringCase("Eagle-Eye Cherry"))).check(matches(isDisplayed()))
        onView(withId(R.id.artistsRecyclerView)).perform(RecyclerViewActions.scrollToPosition<ArtistsAdapter.ViewHolder>(10))
        onView(withText(equalToIgnoringCase("Acid Black Cherry"))).check(matches(isDisplayed()))

    }

    @Test
    fun testDetailActivityLaunchedOnItemClick() {
        Intents.init()

        onView(withId(R.id.search)).perform(click())
        onView(withId(android.support.v7.appcompat.R.id.search_src_text)).perform(ViewActions.typeText("Che"))
        intended(toPackage("me.lamprosgk.lastfm.DetailActivity"))

        Intents.release()
    }

}
