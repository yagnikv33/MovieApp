package com.example.movieapp.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.MovieDetailRes
import com.example.movieapp.data.model.MovieListingRes
import com.example.movieapp.repository.MovieDetailRepository
import com.example.movieapp.repository.MovieRepository
import com.example.movieapp.util.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieVM @Inject constructor(
    private val movieRepo: MovieRepository,
    private val movieDetailRepository: MovieDetailRepository
) : ViewModel() {

    private val _movieList =
        MutableStateFlow<ApiResult<MovieListingRes>>(ApiResult.Error("Initial Error"))
    val movieList: StateFlow<ApiResult<MovieListingRes>> get() = _movieList

    fun searchMovies(query: String) {
        viewModelScope.launch {
            movieRepo.searchMovies(query).collect { result ->
                _movieList.value = result
            }
        }
    }

    private val _movieDetail =
        MutableStateFlow<ApiResult<MovieDetailRes>>(ApiResult.Error("Initial Error"))
    val movieDetail: StateFlow<ApiResult<MovieDetailRes>> get() = _movieDetail

    fun getMovieDetail(id: String, type: String) {
        viewModelScope.launch {
            movieDetailRepository.getMovieDetails(id, type).collect { result ->
                _movieDetail.value = result
            }
        }
    }
}