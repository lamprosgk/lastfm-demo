package me.lamprosgk.lastfm.data

import io.reactivex.Single
import me.lamprosgk.lastfm.data.remote.TopTracksService
import me.lamprosgk.lastfm.model.Track

class TracksRepositoryImpl(private val tracksService: TopTracksService) : TracksRepository {

    override fun getTracks(name: String): Single<List<Track>> =
        tracksService.getTopTracksForArtist(name).map { it.topTracks.track }
}