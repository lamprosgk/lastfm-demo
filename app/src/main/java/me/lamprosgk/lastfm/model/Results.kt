package me.lamprosgk.lastfm.model

import com.google.gson.annotations.SerializedName

data class Results(
    @SerializedName("artistmatches")
    val artistMatches: ArtistMatches,
    @SerializedName("opensearch:itemsPerPage")
    val searchItemsPerPage: String,
    @SerializedName("opensearch:startIndex")
    val searchStartIndex: String,
    @SerializedName("opensearch:totalResults")
    val searchTotalResults: String
)