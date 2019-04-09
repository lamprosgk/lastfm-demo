package me.lamprosgk.lastfm.data

import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import me.lamprosgk.lastfm.BaseTest
import me.lamprosgk.lastfm.data.remote.ArtistsService
import me.lamprosgk.lastfm.model.Artist
import me.lamprosgk.lastfm.model.ArtistSearchResponse
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Response


class ArtistsRepositoryImplTest : BaseTest() {

    @Mock
    lateinit var artistsService: ArtistsService

    private var artistsRepository: ArtistsRepository? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        artistsRepository = ArtistsRepositoryImpl(artistsService)

    }

    @Test
    fun testThatGetArtistsForNameSuccessResponseInvokesCorrectMethods() {

        // given
        `when`(artistsService.getArtistsForName(anyString())).thenReturn(Observable.just(successResponse))

        // when - subscribe to test observable
        val observer = TestObserver<List<Artist>>()
        artistsRepository!!.getArtists("Cher").subscribe(observer)

        // then
        observer.awaitTerminalEvent()
        observer.assertNoErrors()

        val onNextEmission = observer.values()[0]
        assert(onNextEmission[0].name == "Cher")
        assert(onNextEmission.last().name ==  "Cherrie")

        verify(artistsService).getArtistsForName("Cher")

    }

    @Test
    fun testThatGetArtistsForNameErrorResponseTerminatesWithError() {

        `when`(artistsService.getArtistsForName(anyString()))
            .thenReturn(getErrorResponse())

        val observer = TestObserver<List<Artist>>()
        artistsRepository!!.getArtists("Cher").subscribe(observer)

        observer.awaitTerminalEvent()
        observer.assertError(HttpException::class.java)

        verify(artistsService).getArtistsForName("Cher")

    }

    private fun getErrorResponse(): Observable<ArtistSearchResponse> =
        Observable.error(HttpException(Response.error<ArtistSearchResponse>(500, ResponseBody.create(null, "Internal Server Error"))))


}

