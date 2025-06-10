package com.example.moviesapp2.Upcoming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.moviesapp2.RetrofitClient.api

class UpcomingViewModel: ViewModel() {

    val upcomingMovies = Pager(PagingConfig(pageSize = 20)) {
        UpcomingPagingSource(api, "6df68ab00fa7dd19a922a50ec6885352")

    }.flow.cachedIn(viewModelScope)


}
