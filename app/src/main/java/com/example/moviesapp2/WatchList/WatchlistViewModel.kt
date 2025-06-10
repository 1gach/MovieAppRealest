package com.example.moviesapp2.WatchList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviesapp2.MovieDetail.MovieDetailsResponse
import com.example.moviesapp2.WatchList.DatabaseFactory.getDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch




class WatchlistViewModel(application: Application) : AndroidViewModel(application)  {
    private val db = getDatabase(application)
    private val watchlistDao = db.watchlistDao()

    private val dao: WatchlistDao = getDatabase(application).watchlistDao()
    val watchlist = dao.getAll().asLiveData()

    fun toggleMovieInDb(movieResponse: MovieDetailsResponse) {
        val movie = WatchlistMovie(
            id = movieResponse.id,
            title = movieResponse.title,
            posterPath = movieResponse.poster_path ?: "",
            releaseDate = movieResponse.releaseDate,
            runtime = movieResponse.runtime,
            star =  movieResponse.voteAverage

        )
        toggleWatchlist(movie)
    }

    private fun toggleWatchlist(movie: WatchlistMovie) {
        viewModelScope.launch {
            val existing = isInDb(movie.id)
            if (existing) {
                removeFromWatchList(movie)
            } else {
                watchlistDao.insert(movie)
            }
        }
    }

    suspend fun removeFromWatchList(movie: WatchlistMovie) {
        dao.removeFromWatchlist(movie)
    }

    suspend fun isInDb(movieId: Int) = (dao.getMovieById(movieId) != null)

    val watchlistMovies: Flow<List<WatchlistMovie>> = watchlistDao.getAll()
}