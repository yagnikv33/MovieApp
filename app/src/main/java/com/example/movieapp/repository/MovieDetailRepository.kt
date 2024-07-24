package com.example.movieapp.repository

import android.content.Context
import com.example.movieapp.api.service.MovieApiService
import com.example.movieapp.data.model.MovieDetailRes
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

class MovieDetailRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val apiService: MovieApiService
) {

    private suspend fun <T : Any, R, P> parseResponse(
        flowCollector: FlowCollector<ApiResult<T>>,
        apiCall: suspend (R, String, P) -> Response<JsonElement>,
        resultType: Type,
        paramOne: R,
        paramTwo: P
    ) {
        try {
            if (InternetUtil.internetStatus(context)) {
                val response = apiCall.invoke(paramOne, API_KEY, paramTwo)

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


    suspend fun getMovieDetails(movieId: String,type:String): Flow<ApiResult<MovieDetailRes>> = flow {
        val resultType: Type = object : TypeToken<MovieDetailRes>() {}.type
        parseResponse(this,apiService::getMovieDetails,resultType,movieId,type)
    }
}