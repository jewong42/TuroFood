package com.jewong.turofood.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jewong.turofood.api.data.Business
import com.jewong.turofood.api.data.Businesses

class ListRepository {

    val mPizzaBusinesses: MutableLiveData<Businesses> = MutableLiveData(Businesses())
    val mBeerBusinesses = MutableLiveData(Businesses())

    fun updateBeerBusiness(response: Businesses?) {
        mBeerBusinesses.value?.let { businesses ->
            response?.let { response ->

                val list = mutableListOf<Business>().apply {
                    addAll(businesses.businesses)
                    addAll(response.businesses)
                }
                this@ListRepository.mBeerBusinesses.value = Businesses(response.total, list)
            }
        }
    }

    fun updatePizzaBusiness(response: Businesses?) {
        mPizzaBusinesses.value?.let { businesses ->
            response?.let { response ->
                val list = mutableListOf<Business>().apply {
                    addAll(businesses.businesses)
                    addAll(response.businesses)
                }
                this@ListRepository.mPizzaBusinesses.value = Businesses(response.total, list)
            }
        }
    }

}