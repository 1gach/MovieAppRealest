package com.example.moviesapp2.MovieDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.moviesapp2.RetrofitClient
import com.example.moviesapp2.WatchList.MovieDatabase
import com.example.moviesapp2.WatchList.WatchlistMovie
import kotlinx.coroutines.launch


class MovieDetailsViewModel: ViewModel() {
    private val apiKey =  "6df68ab00fa7dd19a922a50ec6885352"

    private val _movieDetails = MutableLiveData<MovieDetailsResponse>()  // replace with your actual response model
    val movieDetails: LiveData<MovieDetailsResponse> get() = _movieDetails

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    val reviews = MutableLiveData<List<Review>>()
    val cast = MutableLiveData<List<Cast>>()

    fun fetchMovieReviews(movieId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getMovieReviews(movieId, apiKey)
                reviews.postValue(response.results)
            } catch (e: Exception) {
                // handle error
            }
        }
    }

    fun fetchMovieCredits(movieId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getMovieCredits(movieId, apiKey)
                cast.postValue(response.cast)
            } catch (e: Exception) {
                // handle error
            }
        }
    }



    fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
            try {
                val result = RetrofitClient.api.getMovieDetails(movieId, apiKey)
                _movieDetails.postValue(result)
            } catch (e: Exception) {
                _error.postValue("Failed to load details: ${e.message}")
            }
        }
    }
}