package com.example.moviesapp2.Data.remote.models

import com.google.gson.annotations.SerializedName

data class MovieDetailsResponse(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("runtime") val runtime: Int,
    val poster_path: String?,
    val backdrop_path: String?,
    @SerializedName("vote_average")
    val voteAverage: Float




)
data class ReviewResponse(
    val id: Int,
    val results: List<Review>
)

data class Review(
    val author: String,
    val content: String
)

data class CastResponse(
    val id: Int,
    val cast: List<Cast>
)

data class Cast(
    val name: String,
    val character: String,
)

