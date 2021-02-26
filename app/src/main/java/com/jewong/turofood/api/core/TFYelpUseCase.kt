package com.jewong.turofood.api.core

import com.jewong.turofood.api.data.Businesses
import com.jewong.turofood.api.data.Coordinates
import com.jewong.turofood.api.request.BusinessesRequest
import com.jewong.turofood.model.ListRepository

class TFYelpUseCase(
    private val mListRepository: ListRepository
) {

    private val mTFYelpApiClient = TFYelpApiClient()

    fun getPizzaBusinesses(
        coordinates: Coordinates,
        offset: Int,
        callback: TFApiCallback<Businesses>
    ) {
        if (offset + REQUEST_LIMIT <= mListRepository.getPizzaBusinessesSize()) {
            val response = mListRepository.getPizzaBusiness(offset, offset + REQUEST_LIMIT)
            callback.onResponse(response, true)
        } else {
            val businessesRequest = BusinessesRequest(
                PIZZA,
                coordinates.latitude,
                coordinates.longitude,
                REQUEST_LIMIT,
                offset
            )
            mTFYelpApiClient.getBusinesses(businessesRequest, callback)
        }
    }

    fun getBeerBusinesses(
        coordinates: Coordinates,
        offset: Int,
        callback: TFApiCallback<Businesses>
    ) {
        if (offset + REQUEST_LIMIT <= mListRepository.getBeerBusinessesSize()) {
            val response = mListRepository.getBeerBusiness(offset, offset + REQUEST_LIMIT)
            callback.onResponse(response, true)
        } else {
            val businessesRequest = BusinessesRequest(
                BEER,
                coordinates.latitude,
                coordinates.longitude,
                REQUEST_LIMIT,
                offset
            )
            mTFYelpApiClient.getBusinesses(businessesRequest, callback)
        }
    }

    companion object {
        const val REQUEST_LIMIT = 10
        const val PIZZA = "pizza"
        const val BEER = "beer"
    }

}