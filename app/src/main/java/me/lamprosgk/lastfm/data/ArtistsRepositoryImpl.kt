package me.lamprosgk.lastfm.data

import io.reactivex.Observable
import me.lamprosgk.lastfm.data.remote.ArtistsService
import me.lamprosgk.lastfm.model.Artist

class ArtistsRepositoryImpl(private val artistsService: ArtistsService) : ArtistsRepository {

    override fun getArtists(name: String): Observable<List<Artist>> =
        artistsService.getArtistsForName(name).map{ it.results.artistMatches.artist }

}