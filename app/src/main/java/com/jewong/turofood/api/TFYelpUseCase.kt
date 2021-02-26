package com.jewong.turofood.api

import com.jewong.turopizzaapp.api.data.Businesses
import com.jewong.turopizzaapp.api.data.Coordinates
import com.jewong.turopizzaapp.api.request.BusinessesRequest

class TFYelpUseCase {

    private val mTFYelpApiClient = TFYelpApiClient()

    fun getPizzaBusinesses(coordinates: Coordinates, callback: TFApiCallback<Businesses>) {
        val businessesRequest = BusinessesRequest(PIZZA, "${coordinates.latitude}", "${coordinates.longitude}")
        mTFYelpApiClient.getBusinesses(businessesRequest, callback)
    }

    fun getBeerBusinesses(coordinates: Coordinates, callback: TFApiCallback<Businesses>) {
        val businessesRequest = BusinessesRequest(BEER, "${coordinates.latitude}", "${coordinates.longitude}")
        mTFYelpApiClient.getBusinesses(businessesRequest, callback)
    }

    companion object {
        const val PIZZA = "pizza"
        const val BEER = "beer"
    }

}