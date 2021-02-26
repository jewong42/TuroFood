package com.jewong.turofood.api

import com.jewong.turofood.BuildConfig
import com.jewong.turopizzaapp.api.data.Businesses
import com.jewong.turopizzaapp.api.request.BusinessesRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface TFYelpApi {

    @Headers("Authorization: Bearer ${BuildConfig.YELP_API_KEY}")
    @GET("v3/businesses/search")
    fun getBusinesses(
        @Query("term") term: String,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String
    ): Call<Businesses>

}