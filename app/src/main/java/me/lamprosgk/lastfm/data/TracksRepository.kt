package me.lamprosgk.lastfm.data

import io.reactivex.Single
import me.lamprosgk.lastfm.model.Track

interface TracksRepository {

    fun getTracks(name: String): Single<List<Track>>
}