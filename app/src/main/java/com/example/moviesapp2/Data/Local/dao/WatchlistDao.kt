package com.example.moviesapp2.Data.Local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesapp2.WatchList.WatchlistMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: WatchlistMovie)

    @Query("SELECT * FROM watchlist WHERE id = :movieId LIMIT 1")
    suspend fun getMovieById(movieId: Int): WatchlistMovie?

    @Delete
    suspend fun removeFromWatchlist(movie: WatchlistMovie)

    @Query("SELECT * FROM watchlist")
    fun getAll(): Flow<List<WatchlistMovie>>


}
