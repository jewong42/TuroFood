package com.jewong.turofood.ui

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jewong.turofood.api.core.TFApiCallback
import com.jewong.turofood.api.core.TFYelpUseCase
import com.jewong.turofood.api.data.Business
import com.jewong.turofood.api.data.Businesses
import com.jewong.turofood.api.data.Coordinates
import com.jewong.turofood.data.ListRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject

class ListViewModel : ViewModel() {

    private val mTFYelpUseCase = TFYelpUseCase()
    private val mListRepository = ListRepository()
    val mBusinesses = MutableLiveData<Businesses>()
    private val mPizzaBusinesses: PublishSubject<Businesses?> = PublishSubject.create()
    private val mBeerBusinesses: PublishSubject<Businesses?> = PublishSubject.create()
    private val mDisposable: CompositeDisposable = CompositeDisposable()
    val mLoadingVisibility = MutableLiveData(View.GONE)
    val mShowError = MutableLiveData("")

    init {
        mDisposable.add(
            Observable
                .zip(mPizzaBusinesses, mBeerBusinesses, { u, p -> updateList(u, p) })
                .subscribe()
        )
    }

    override fun onCleared() {
        mDisposable.dispose()
        super.onCleared()
    }

    fun getBusinesses() {
        if (mLoadingVisibility.value == View.VISIBLE) return
        mLoadingVisibility.value = View.VISIBLE
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
        mLoadingVisibility.value = View.GONE
    }

    private fun getBeerBusinesses(coordinates: Coordinates) {
        val offset = mListRepository.mBeerBusinesses.value?.businesses?.size ?: 0
        mTFYelpUseCase.getBeerBusinesses(coordinates, offset, object : TFApiCallback<Businesses> {
            override fun onResponse(response: Businesses?) {
                mListRepository.updateBeerBusiness(response)
                mBeerBusinesses.onNext(response)
            }

            override fun onFailure(throwable: Throwable) {
                mShowError.value = throwable.localizedMessage
                mBeerBusinesses.onNext(Businesses())
            }
        })
    }

    private fun getPizzaBusinesses(coordinates: Coordinates) {
        val offset = mListRepository.mPizzaBusinesses.value?.businesses?.size ?: 0
        mTFYelpUseCase.getPizzaBusinesses(coordinates, offset, object : TFApiCallback<Businesses> {
            override fun onResponse(response: Businesses?) {
                mListRepository.updatePizzaBusiness(response)
                mPizzaBusinesses.onNext(response)
            }

            override fun onFailure(throwable: Throwable) {
                mShowError.value = throwable.localizedMessage
                mPizzaBusinesses.onNext(Businesses())
            }
        })
    }

}