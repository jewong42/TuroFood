package com.jewong.turopizzaapp.api.data

import com.google.gson.annotations.SerializedName

data class Businesses(

	@SerializedName("total") val total: Int,
	@SerializedName("businesses") val businesses: List<Business>
)