package com.example.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class MovieErrorRes(

	@field:SerializedName("Response")
	val response: String? = null,

	@field:SerializedName("Error")
	val error: String? = null
)
