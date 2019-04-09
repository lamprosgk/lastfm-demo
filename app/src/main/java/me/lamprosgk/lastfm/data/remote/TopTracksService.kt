package me.lamprosgk.lastfm.data.remote

import io.reactivex.Single
import me.lamprosgk.lastfm.API_KEY
import me.lamprosgk.lastfm.model.TopTracksResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TopTracksService {

    companion object {
        const val TRACKS_LIMIT = 10
    }

    @GET("?method=artist.gettoptracks&limit=$TRACKS_LIMIT&api_key=$API_KEY&format=json")
    fun getTopTracksForArtist(@Query("artist") artistName: String): Single<TopTracksResponse>

}