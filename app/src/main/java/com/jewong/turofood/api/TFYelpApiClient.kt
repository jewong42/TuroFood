package com.jewong.turofood.api

import com.jewong.turofood.BuildConfig
import com.jewong.turopizzaapp.api.data.Businesses
import com.jewong.turopizzaapp.api.request.BusinessesRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class TFYelpApiClient {

    private val mRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.YELP_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val mTFYelpApi: TFYelpApi = mRetrofit.create(TFYelpApi::class.java)

    fun getBusinesses(request: BusinessesRequest, callback: TFApiCallback<Businesses>) {
        mTFYelpApi.getBusinesses(request.term, request.latitude, request.longitude).enqueue(object: Callback<Businesses> {
            override fun onResponse(call: Call<Businesses>, response: Response<Businesses>) {
                callback.onResponse(response.body())
            }

            override fun onFailure(call: Call<Businesses>, t: Throwable) {
                callback.onFailure(t)
            }
        })
    }

}