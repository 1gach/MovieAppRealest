package com.example.moviesapp2.WatchList

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WatchlistMovie::class], version = 2)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun watchlistDao(): WatchlistDao
}