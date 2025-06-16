package com.example.moviesapp2.WatchList

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviesapp2.Data.Local.Entities.WatchlistMovie
import com.example.moviesapp2.Data.Local.dao.WatchlistDao

@Database(entities = [WatchlistMovie::class], version = 2)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun watchlistDao(): WatchlistDao
}