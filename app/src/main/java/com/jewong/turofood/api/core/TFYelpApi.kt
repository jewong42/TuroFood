package com.jewong.turofood.api.core

import com.jewong.turofood.BuildConfig
import com.jewong.turofood.api.data.Businesses
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface TFYelpApi {

    @Headers("Authorization: Bearer ${BuildConfig.YELP_API_KEY}")
    @GET("v3/businesses/search")
    fun getBusinesses(
        @Query("term") term: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Call<Businesses>

}