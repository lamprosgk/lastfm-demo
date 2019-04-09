package me.lamprosgk.lastfm.model

import com.google.gson.annotations.SerializedName

data class TopTracksResponse(
    @SerializedName("toptracks")
    val topTracks: TopTracks
)