package me.lamprosgk.lastfm.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Artist(
    @SerializedName("image")
    val images: List<Image>,
    @SerializedName("listeners")
    val listeners: String?,
    @SerializedName("mbid")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("streamable")
    val streamable: String?,
    @SerializedName("url")
    val url: String
) : Parcelable