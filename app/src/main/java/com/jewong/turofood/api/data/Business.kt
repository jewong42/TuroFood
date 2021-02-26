package com.jewong.turopizzaapp.api.data

import com.google.gson.annotations.SerializedName

data class Business(

	@SerializedName("rating") val rating: Double,
	@SerializedName("price") val price: String,
	@SerializedName("phone") val phone: String,
	@SerializedName("id") val id: String,
	@SerializedName("categories") val categories: List<Category>,
	@SerializedName("review_count") val review_count: Int,
	@SerializedName("name") val name: String,
	@SerializedName("url") val url: String,
	@SerializedName("coordinates") val coordinates: Coordinates,
	@SerializedName("image_url") val image_url: String,
	@SerializedName("location") val location: Location
)