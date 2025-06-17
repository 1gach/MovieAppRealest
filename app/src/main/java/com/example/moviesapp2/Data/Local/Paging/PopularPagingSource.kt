package com.example.moviesapp2.Data.Local.Paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesapp2.Data.remote.api.MovieApi
import com.example.moviesapp2.Data.remote.models.Movie

class PopularPagingSource (
    private val api: MovieApi,
    private val apiKey: String
) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return try {
            val response = api.getPopularMovies(apiKey, page)
            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page >= response.totalPages) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}