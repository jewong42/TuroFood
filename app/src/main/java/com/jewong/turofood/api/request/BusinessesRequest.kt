package com.jewong.turofood.api.request

import com.google.gson.annotations.SerializedName

data class BusinessesRequest(

    @SerializedName("term") val term: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("limit") val limit: Int,
    @SerializedName("offset") val offset: Int
)