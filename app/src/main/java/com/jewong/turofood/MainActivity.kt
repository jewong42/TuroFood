package com.jewong.turofood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jewong.turofood.api.TFApiCallback
import com.jewong.turofood.api.TFYelpApiClient
import com.jewong.turofood.api.TFYelpUseCase
import com.jewong.turopizzaapp.api.data.Business
import com.jewong.turopizzaapp.api.data.Businesses
import com.jewong.turopizzaapp.api.data.Coordinates

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
    }

}