package me.lamprosgk.lastfm.data.remote

import io.reactivex.Single
import me.lamprosgk.lastfm.model.TopTracksResponse

class MockTracksServiceImpl : TopTracksService {

    private val mockResponse = MockHelper.tracksResponse

    override fun getTopTracksForArtist(artistName: String): Single<TopTracksResponse> = Single.just(mockResponse)
}