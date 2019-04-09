package me.lamprosgk.lastfm.util

import me.lamprosgk.lastfm.model.Artist

fun Artist.getImageUrl(size: String = "medium") = images.first { it.size == size }.url