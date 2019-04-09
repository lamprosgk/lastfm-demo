package me.lamprosgk.lastfm.data

import io.reactivex.Observable
import me.lamprosgk.lastfm.model.Artist

interface ArtistsRepository {

    fun getArtists(name: String): Observable<List<Artist>>
}