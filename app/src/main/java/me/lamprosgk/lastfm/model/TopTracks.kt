package me.lamprosgk.lastfm.model

import com.google.gson.annotations.SerializedName

data class TopTracks(@SerializedName("track") val track: List<Track>)