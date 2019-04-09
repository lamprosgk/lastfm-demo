package me.lamprosgk.lastfm.ui.search

import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import me.lamprosgk.lastfm.BaseTest
import me.lamprosgk.lastfm.data.ArtistsRepository
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class SearchPresenterTest : BaseTest() {

    @Mock
    lateinit var view: SearchContract.View
    @Mock
    lateinit var artistsRepository: ArtistsRepository

    lateinit var searchPresenter: SearchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        // replace Schedulers for test
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        searchPresenter = SearchPresenter(view, artistsRepository)
    }

    @Test
    fun testGetArtistResultIsValid() {
        `when`(artistsRepository.getArtists(anyString())).thenReturn(Observable.just(successResponse.results.artistMatches.artist))

        searchPresenter.getArtistsForName("Cher")

        // check that the right methods called in the view and in right order
        verify(view).showLoading(true)
        verify(view).showLoading(false)
        verify(view).showArtistMatches(successResponse.results.artistMatches.artist)
        verify(view, never()).showError(Throwable())
    }

    @Test
    fun testThatErrorIsShownOnFailure() {
        val throwable = Throwable()

        `when`(artistsRepository.getArtists(anyString())).thenReturn(Observable.error(throwable))
        searchPresenter.getArtistsForName("NotAValidName")

        verify(view).showLoading(true)
        verify(view).showLoading(false)
        verify(view).showError(Throwable())
        verify(view, never()).showArtistMatches(anyList())
    }
}