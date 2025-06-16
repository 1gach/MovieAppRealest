package com.example.moviesapp2.WatchList

import android.content.Context
import androidx.room.Room
import com.example.moviesapp2.Data.Local.db.MovieDatabase

object DatabaseFactory {
    private var INSTANCE: MovieDatabase? = null

    fun getDatabase(context: Context): MovieDatabase {
        return INSTANCE ?: synchronized(this) {
            Room.databaseBuilder(
                context.applicationContext,
                MovieDatabase::class.java,
                "my_database"
            )
                .fallbackToDestructiveMigration()
                .build().also { INSTANCE = it }
        }
    }
}