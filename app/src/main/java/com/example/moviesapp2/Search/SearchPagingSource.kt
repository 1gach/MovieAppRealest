package com.example.moviesapp2.Search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesapp2.MovieApi


class SearchPagingSource(
    private val api: MovieApi,
    private val apiKey: String,
    private val query: String
) : PagingSource<Int,Searches>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Searches> {
        return try {
            val page = params.key ?: 1
            val response = api.searchMovies(apiKey, query, page)
            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page < response.total_pages) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Searches>): Int? {
        return state.anchorPosition
    }
}

