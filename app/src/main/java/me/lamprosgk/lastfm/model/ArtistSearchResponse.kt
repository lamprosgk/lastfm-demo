package me.lamprosgk.lastfm.model

import com.google.gson.annotations.SerializedName

data class ArtistSearchResponse(
    @SerializedName("results")
    val results: Results
)