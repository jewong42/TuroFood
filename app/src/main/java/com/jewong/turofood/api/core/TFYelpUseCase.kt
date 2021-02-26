package com.jewong.turofood.api.core

import com.jewong.turofood.api.data.Businesses
import com.jewong.turofood.api.data.Coordinates
import com.jewong.turofood.api.request.BusinessesRequest

class TFYelpUseCase {

    private val mTFYelpApiClient = TFYelpApiClient()

    fun getPizzaBusinesses(
        coordinates: Coordinates,
        offset: Int,
        callback: TFApiCallback<Businesses>
    ) {
        val businessesRequest = BusinessesRequest(
            PIZZA,
            coordinates.latitude,
            coordinates.longitude,
            REQUEST_LIMIT,
            offset
        )
        mTFYelpApiClient.getBusinesses(businessesRequest, callback)
    }

    fun getBeerBusinesses(
        coordinates: Coordinates,
        offset: Int,
        callback: TFApiCallback<Businesses>
    ) {
        val businessesRequest = BusinessesRequest(
            BEER,
            coordinates.latitude,
            coordinates.longitude,
            REQUEST_LIMIT,
            offset
        )
        mTFYelpApiClient.getBusinesses(businessesRequest, callback)
    }

    companion object {
        const val REQUEST_LIMIT = 10
        const val PIZZA = "pizza"
        const val BEER = "beer"
    }

}