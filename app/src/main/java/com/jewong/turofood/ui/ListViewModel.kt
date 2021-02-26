package com.jewong.turofood.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jewong.turofood.api.core.TFApiCallback
import com.jewong.turofood.api.core.TFYelpUseCase
import com.jewong.turofood.api.data.Business
import com.jewong.turofood.api.data.Businesses
import com.jewong.turofood.api.data.Coordinates
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject

class ListViewModel : ViewModel() {

    private val mTFYelpUseCase = TFYelpUseCase()
    val mBusinesses = MutableLiveData<Businesses>()
    private val mPizzaBusinesses: PublishSubject<Businesses> = PublishSubject.create()
    private val mBeerBusinesses: PublishSubject<Businesses> = PublishSubject.create()
    private val mDisposable: Disposable = Observable
        .combineLatest(mPizzaBusinesses, mBeerBusinesses, { u, p -> updateList(u, p) })
        .subscribe()

    override fun onCleared() {
        mDisposable.dispose()
        super.onCleared()
    }

    fun getBusinesses() {
        val coordinates = Coordinates(37.7876, -122.4342)
        getBeerBusinesses(coordinates)
        getPizzaBusinesses(coordinates)
    }

    private fun updateList(pizzaBusinesses: Businesses?, beerBusinesses: Businesses?) {
        val list = mutableListOf<Business>().run {
            pizzaBusinesses?.businesses?.let { addAll(it) }
            beerBusinesses?.businesses?.let { addAll(it) }
            sortedBy { it.distance }
        }
        mBusinesses.value = Businesses(list.size, list)
    }

    private fun getBeerBusinesses(coordinates: Coordinates) {
        mTFYelpUseCase.getBeerBusinesses(coordinates, object : TFApiCallback<Businesses> {
            override fun onResponse(response: Businesses?) {
                response?.let { mBeerBusinesses.onNext(it) }
            }

            override fun onFailure(throwable: Throwable) {
                Log.d("Jerry", "onFailure: ${throwable.localizedMessage}")
            }
        })
    }

    private fun getPizzaBusinesses(coordinates: Coordinates) {
        mTFYelpUseCase.getPizzaBusinesses(coordinates, object : TFApiCallback<Businesses> {
            override fun onResponse(response: Businesses?) {
                response?.let { mPizzaBusinesses.onNext(it) }
            }

            override fun onFailure(throwable: Throwable) {
                Log.d("Jerry", "onFailure: ${throwable.localizedMessage}")
            }
        })
    }

}