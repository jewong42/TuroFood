package com.jewong.turopizzaapp.api.data

import com.google.gson.annotations.SerializedName

data class Category(

	@SerializedName("alias") val alias: String,
	@SerializedName("title") val title: String
)