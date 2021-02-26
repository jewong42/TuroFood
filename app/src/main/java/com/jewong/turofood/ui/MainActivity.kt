package com.jewong.turofood.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jewong.turofood.R
import com.jewong.turofood.api.core.TFApiCallback
import com.jewong.turofood.api.core.TFYelpUseCase
import com.jewong.turofood.api.data.Businesses
import com.jewong.turofood.api.data.Coordinates

class MainActivity : AppCompatActivity() {

    private val mTFYelpUseCase = TFYelpUseCase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val coordinates = Coordinates(37.7876, -122.4342)
        mTFYelpUseCase.getBeerBusinesses(coordinates, object : TFApiCallback<Businesses> {
            override fun onResponse(response: Businesses?) {
                response?.businesses?.forEach { business ->
                    Log.d("Jerry", business.toString())
                }
            }

            override fun onFailure(throwable: Throwable) {
                Log.d("Jerry", "onFailure: ${throwable.localizedMessage}")
            }
        })
        mTFYelpUseCase.getPizzaBusinesses(coordinates, object : TFApiCallback<Businesses> {
            override fun onResponse(response: Businesses?) {
                response?.businesses?.forEach { business ->
                    Log.d("Jerry", business.toString())
                }
            }

            override fun onFailure(throwable: Throwable) {
                Log.d("Jerry", "onFailure: ${throwable.localizedMessage}")
            }
        })
    }

}