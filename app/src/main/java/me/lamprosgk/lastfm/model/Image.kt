package me.lamprosgk.lastfm.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(
    @SerializedName("#text")
    val url: String,
    @SerializedName("size")
    val size: String            // "small", "medium", "large", "extralarge"
) : Parcelable