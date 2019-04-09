package me.lamprosgk.lastfm.data.remote

import com.google.gson.Gson
import me.lamprosgk.lastfm.model.ArtistSearchResponse
import me.lamprosgk.lastfm.model.TopTracksResponse


class MockHelper {

    companion object {

        private val artistsJson = readAsString("artist_search.json")
        private val tracksJson = readAsString("top_tracks_search.json")

        val artistsResponse: ArtistSearchResponse =
            Gson().fromJson<ArtistSearchResponse>(artistsJson, ArtistSearchResponse::class.java)

        val tracksResponse: TopTracksResponse =
            Gson().fromJson<TopTracksResponse>(tracksJson, TopTracksResponse::class.java)

        private fun readAsString(path: String): String {
            return this.javaClass.classLoader.getResourceAsStream(path).bufferedReader().use { it.readText() }



        }



    }

}