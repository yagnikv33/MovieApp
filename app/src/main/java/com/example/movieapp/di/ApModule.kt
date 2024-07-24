package com.example.movieapp.di

import android.content.Context
import com.example.movieapp.api.service.MovieApiService
import com.example.movieapp.repository.MovieDetailRepository
import com.example.movieapp.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideMovieApiService(): MovieApiService {
        return Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/?apikey=ed2aa0b8")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        @ApplicationContext context: Context,
        apiService: MovieApiService
    ): MovieRepository {
        return MovieRepository(context, apiService = apiService)
    }

    @Provides
    @Singleton
    fun provideMovieDetailRepository(
        @ApplicationContext context: Context,
        apiService: MovieApiService
    ): MovieDetailRepository {
        return MovieDetailRepository(context, apiService = apiService)
    }
}