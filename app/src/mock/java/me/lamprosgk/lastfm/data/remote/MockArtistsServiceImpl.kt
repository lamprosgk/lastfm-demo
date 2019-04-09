package me.lamprosgk.lastfm.data.remote

import io.reactivex.Observable
import me.lamprosgk.lastfm.model.ArtistSearchResponse

class MockArtistsServiceImpl : ArtistsService {

    private val mockResponse = MockHelper.artistsResponse

    override fun getArtistsForName(artistName: String): Observable<ArtistSearchResponse> {
        return Observable.just(mockResponse)
    }

}