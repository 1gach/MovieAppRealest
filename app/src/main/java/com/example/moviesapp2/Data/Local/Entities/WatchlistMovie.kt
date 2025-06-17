package com.example.moviesapp2.Data.Local.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watchlist")
data class WatchlistMovie(
    @PrimaryKey val id: Int,
    val title: String,
    val releaseDate: String,
    val runtime: Int,
    val posterPath: String,
    val star : Float

)