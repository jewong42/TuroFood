package com.jewong.turofood.data

import com.jewong.turofood.api.data.Business
import com.jewong.turofood.api.data.Businesses

class ListRepository {

    var mPizzaBusinesses = Businesses()
    var mBeerBusinesses = Businesses()
    val mBusinesses= mutableListOf<Business>()

    fun updateBusinesses(businesses: List<Business>) {
        mBusinesses.addAll(businesses)
    }

    fun updateBeerBusiness(response: Businesses?) {
        mBeerBusinesses.let { businesses ->
            response?.let { response ->

                val list = mutableListOf<Business>().apply {
                    addAll(businesses.businesses)
                    addAll(response.businesses)
                }
                this@ListRepository.mBeerBusinesses = Businesses(response.total, list)
            }
        }
    }

    fun updatePizzaBusiness(response: Businesses?) {
        mPizzaBusinesses.let { businesses ->
            response?.let { response ->
                val list = mutableListOf<Business>().apply {
                    addAll(businesses.businesses)
                    addAll(response.businesses)
                }
                this@ListRepository.mPizzaBusinesses = Businesses(response.total, list)
            }
        }
    }

}