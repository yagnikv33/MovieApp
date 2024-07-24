package com.example.movieapp.repository

import android.content.Context
import com.example.movieapp.api.service.MovieApiService
import com.example.movieapp.data.model.MovieListingRes
import com.example.movieapp.util.ApiResult
import com.example.movieapp.util.AppConstance.ApiConstant.API_KEY
import com.example.movieapp.util.InternetUtil
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.lang.reflect.Type
import javax.inject.Inject

class MovieRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val apiService: MovieApiService
) {

    private suspend fun <T : Any, R> parseResponse(
        flowCollector: FlowCollector<ApiResult<T>>,
        apiCall: suspend (R, String) -> Response<JsonElement>,
        resultType: Type,
        paramOne: R
    ) {
        try {
            if (InternetUtil.internetStatus(context)) {
                val response = apiCall.invoke(paramOne, API_KEY)

                if (response.isSuccessful) {
                    val jsonString = response.body()?.toString()

                    if (!jsonString.isNullOrEmpty()) {
                        val result: T = Gson().fromJson(jsonString, resultType)
                        flowCollector.emit(ApiResult.Success(result))
                    } else {
                        flowCollector.emit(ApiResult.Error("No Data found.."))
                    }
                } else {
                    flowCollector.emit(ApiResult.Error("API error: ${response.message()}"))
                }
            } else {
                flowCollector.emit(ApiResult.Error("No internet available"))
            }
        } catch (e: Exception) {
            flowCollector.emit(ApiResult.Error("An unexpected error occurred"))
        }
    }

    suspend fun searchMovies(query: String): Flow<ApiResult<MovieListingRes>> = flow {
        val resultType: Type = object : TypeToken<MovieListingRes>() {}.type
        parseResponse(this, apiService::searchMovies, resultType, query)
    }


}