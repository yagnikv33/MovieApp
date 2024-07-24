package com.example.movieapp.api.service

import com.example.movieapp.data.model.MovieListingRes
import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET(".")
    suspend fun searchMovies(
        @Query("s") searchKeyword: String,
        @Query("apikey") apiKey: String
    ): Response<JsonElement>

    @GET(".")
    suspend fun getMovieDetails(
        @Query("i") movieId: String,
        @Query("apikey") apiKey: String,
        @Query("plot") plot: String
    ): Response<JsonElement>
}