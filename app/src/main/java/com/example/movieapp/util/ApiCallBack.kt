package com.example.movieapp.util

sealed class ApiResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : ApiResult<T>()
    data class Error(val message: String) : ApiResult<Nothing>()
}