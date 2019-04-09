package me.lamprosgk.lastfm

import com.google.gson.Gson
import me.lamprosgk.lastfm.model.ArtistSearchResponse

open class BaseTest {

    private val json = readAsString("artist_search.json")
    val successResponse: ArtistSearchResponse = Gson().fromJson<ArtistSearchResponse>(json, ArtistSearchResponse::class.java)

    private fun readAsString(path: String): String {
        return this.javaClass.classLoader.getResourceAsStream(path).bufferedReader().use { it.readText() }
    }
}

