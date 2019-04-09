package me.lamprosgk.lastfm.model

import com.google.gson.annotations.SerializedName

data class ArtistMatches(
    @SerializedName("artist")
    val artist: List<Artist>
)