package com.example.movieapp.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovieListingRes(

    @field:SerializedName("Response")
    val response: String? = null,

    @field:SerializedName("totalResults")
    val totalResults: String? = null,

    @field:SerializedName("Search")
    val search: List<SearchItem?>? = null
) : Serializable

data class SearchItem(

    @field:SerializedName("Type")
    val type: String? = null,

    @field:SerializedName("Year")
    val year: String? = null,

    @field:SerializedName("imdbID")
    val imdbID: String? = null,

    @field:SerializedName("Poster")
    val poster: String? = null,

    @field:SerializedName("Title")
    val title: String? = null
) : Serializable
