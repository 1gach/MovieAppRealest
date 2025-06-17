package com.example.moviesapp2.Data.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    @SerializedName("total_pages") val totalPages: Int,
)

@kotlinx.parcelize.Parcelize
data class Movie(
    val id: Int,
    @SerializedName("poster_path") val posterPath: String?,

): Parcelable