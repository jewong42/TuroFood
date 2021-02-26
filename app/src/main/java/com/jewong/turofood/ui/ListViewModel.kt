package com.jewong.turofood.ui

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jewong.turofood.api.core.TFApiCallback
import com.jewong.turofood.api.core.TFYelpUseCase
import com.jewong.turofood.api.data.Business
import com.jewong.turofood.api.data.Businesses
import com.jewong.turofood.api.data.Coordinates
import com.jewong.turofood.model.ListRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.launch


class ListViewModel(application: Application) : AndroidViewModel(application) {

    private val mListRepository = ListRepository(getApplication())
    private val mTFYelpUseCase = TFYelpUseCase(mListRepository)
    private val mPizzaBusinesses: PublishSubject<Businesses?> = PublishSubject.create()
    private val mBeerBusinesses: PublishSubject<Businesses?> = PublishSubject.create()
    private val mDisposable: CompositeDisposable = CompositeDisposable()
    val mBusinesses = MutableLiveData<List<Business>>()
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
        val coordinates = Coordinates(OFFICE_LAT, OFFICE_LONG)
        getBeerBusinesses(coordinates)
        getPizzaBusinesses(coordinates)
    }

    private fun updateList(pizzaBusinesses: Businesses?, beerBusinesses: Businesses?) {
        val list = mutableListOf<Business>().run {
            pizzaBusinesses?.businesses?.let { addAll(it) }
            beerBusinesses?.businesses?.let { addAll(it) }
            sortedBy { it.distance }
        }
        mListRepository.updateBusinesses(list)
        mBusinesses.value = mListRepository.mBusinesses
        mLoadingVisibility.value = View.GONE
    }

    private fun getBeerBusinesses(coordinates: Coordinates) {
        val offset = mListRepository.mBeerOffset
        mTFYelpUseCase.getBeerBusinesses(coordinates, offset, object : TFApiCallback<Businesses> {
            override fun onResponse(response: Businesses?, isCache: Boolean) {
                viewModelScope.launch {
                    mListRepository.updateBeerBusinesses(response, isCache)
                    mBeerBusinesses.onNext(response)
                }
            }

            override fun onFailure(throwable: Throwable) {
                mShowError.value = throwable.localizedMessage
                mBeerBusinesses.onNext(Businesses())
            }
        })
    }

    private fun getPizzaBusinesses(coordinates: Coordinates) {
        val offset = mListRepository.mPizzaOffset
        mTFYelpUseCase.getPizzaBusinesses(coordinates, offset, object : TFApiCallback<Businesses> {
            override fun onResponse(response: Businesses?, isCache: Boolean) {
                viewModelScope.launch {
                    mListRepository.updatePizzaBusinesses(response, isCache)
                    mPizzaBusinesses.onNext(response)
                }
            }

            override fun onFailure(throwable: Throwable) {
                mShowError.value = throwable.localizedMessage
                mPizzaBusinesses.onNext(Businesses())
            }
        })
    }

    companion object {
        const val OFFICE_LAT = 37.789760
        const val OFFICE_LONG = -122.402520
    }

}