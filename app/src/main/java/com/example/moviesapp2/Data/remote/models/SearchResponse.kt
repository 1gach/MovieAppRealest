package com.example.moviesapp2.Data.remote.models

data class SearchResponse(
    val page: Int,
    val results: List<Searches>,

    val total_pages: Int,
    val total_results: Int
)

data class Searches(
    val id: Int,
    val title: String,
    val poster_path: String?,
    val release_date: String
)
