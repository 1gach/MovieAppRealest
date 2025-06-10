package com.example.moviesapp2.Popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.moviesapp2.NowPlaying.Movie
import com.example.moviesapp2.RetrofitClient.api
import com.example.moviesapp2.Upcoming.UpcomingPagingSource
import kotlinx.coroutines.launch

class PopularViewModel: ViewModel() {

    val popularMovies = Pager(PagingConfig(pageSize = 20)) {
        PopularPagingSource(api, "6df68ab00fa7dd19a922a50ec6885352")

    }.flow.cachedIn(viewModelScope)


    private val _popularPosters = MutableLiveData<List<Movie>>()
    val popularPosters: LiveData<List<Movie>> get() = _popularPosters

    fun load5PopularPosters() {
        viewModelScope.launch {
            try {
                val response = api.getPopularMovies("6df68ab00fa7dd19a922a50ec6885352", 1)
                _popularPosters.value = response.results.take(5) // take only 5
            } catch (e: Exception) {
                _popularPosters.value = emptyList()
            }
        }
    }


}