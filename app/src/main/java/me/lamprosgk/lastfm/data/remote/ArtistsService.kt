package me.lamprosgk.lastfm.data.remote

import io.reactivex.Observable
import me.lamprosgk.lastfm.API_KEY
import me.lamprosgk.lastfm.model.ArtistSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistsService {

    @GET("?method=artist.search&api_key=$API_KEY&format=json")
    fun getArtistsForName(@Query("artist") artistName: String): Observable<ArtistSearchResponse>

}