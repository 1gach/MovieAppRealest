package com.example.moviesapp2.UI.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.moviesapp2.Data.remote.RetrofitClient.api
import com.example.moviesapp2.Data.Local.Paging.TopRatedPagingSource

class TopRatedViewModel : ViewModel() {

    val topRatedMovies = Pager(PagingConfig(pageSize = 20)) {
        TopRatedPagingSource(api, "6df68ab00fa7dd19a922a50ec6885352")

    }.flow.cachedIn(viewModelScope)


}