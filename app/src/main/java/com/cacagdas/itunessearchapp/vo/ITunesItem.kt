package com.cacagdas.itunessearchapp.vo

import com.google.gson.annotations.SerializedName

data class ITunesItem(
        @field:SerializedName("trackId") val id: Int,
        @field:SerializedName("collectionName") val title: String,
        @field:SerializedName("artworkUrl100") val posterPath: String?,
        val releaseDate: String?
)