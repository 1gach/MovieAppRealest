package com.example.moviesapp2

import com.example.moviesapp2.MovieDetail.CastResponse
import com.example.moviesapp2.MovieDetail.MovieDetailsResponse
import com.example.moviesapp2.MovieDetail.ReviewResponse
import com.example.moviesapp2.NowPlaying.MovieResponse
import com.example.moviesapp2.Search.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

    interface MovieApi {
        @GET("movie/now_playing")
        suspend fun getNowPlayingMovies(
            @Query("api_key") apiKey: String,
            @Query("page") page: Int
        ): MovieResponse

        @GET("movie/upcoming")
        suspend fun getUpcomingMovies(
            @Query("api_key") apiKey: String,
            @Query("page") page: Int
        ): MovieResponse


        @GET("movie/top_rated")
        suspend fun getTopRatedMovies(
            @Query("api_key") apiKey: String,
            @Query("page") page: Int
        ): MovieResponse

        @GET("movie/popular")
        suspend fun getPopularMovies(
            @Query("api_key") apiKey: String,
            @Query("page") page: Int
        ): MovieResponse

        @GET("movie/{movie_id}")
        suspend fun getMovieDetails(
            @Path("movie_id") movieId: Int,
            @Query("api_key") apiKey: String
        ): MovieDetailsResponse

        @GET("search/movie")
        suspend fun searchMovies(
            @Query("api_key") apiKey: String,
            @Query("query") query: String,
            @Query("page") page: Int
        ): SearchResponse

        @GET("movie/{movie_id}/reviews")
        suspend fun getMovieReviews(
            @Path("movie_id") movieId: Int,
            @Query("api_key") apiKey: String
        ): ReviewResponse

        @GET("movie/{movie_id}/credits")
        suspend fun getMovieCredits(
            @Path("movie_id") movieId: Int,
            @Query("api_key") apiKey: String
        ): CastResponse
    }
