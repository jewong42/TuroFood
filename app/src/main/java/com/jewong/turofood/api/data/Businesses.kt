package com.jewong.turofood.api.data

import com.google.gson.annotations.SerializedName

data class Businesses(

	@SerializedName("total") val total: Int = 0,
	@SerializedName("businesses") val businesses: List<Business> = mutableListOf()
)