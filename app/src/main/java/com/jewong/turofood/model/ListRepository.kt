package com.jewong.turofood.model

import android.app.Application
import com.jewong.turofood.api.data.Business
import com.jewong.turofood.api.data.Businesses

class ListRepository(
    application: Application
) {

    private val db = AppDatabase.getInstance(application)
    private val mUserDao = db.userDao
    private var mUser: User
    val mBusinesses = mutableListOf<Business>()
    var mPizzaOffset = 0
    var mBeerOffset = 0

    init {
        if (mUserDao.loadUser() == null) mUserDao.insertUser(User())
        mUser = mUserDao.loadUser()!!
    }

    fun updateBusinesses(businesses: List<Business>) {
        mBusinesses.addAll(businesses)
    }

    fun getBeerBusinessesSize(): Int {
        return mUser.beerBusinesses.businesses.size
    }

    fun getPizzaBusinessesSize(): Int {
        return mUser.beerBusinesses.businesses.size
    }

    fun getPizzaBusiness(offset: Int, count: Int): Businesses {
        val list = mUser.pizzaBusinesses.businesses.subList(offset, count)
        val total = mUser.pizzaBusinesses.total
        return Businesses(total, list)
    }

    fun getBeerBusiness(offset: Int, count: Int): Businesses {
        val list = mUser.beerBusinesses.businesses.subList(offset, count)
        val total = mUser.beerBusinesses.total
        return Businesses(total, list)
    }

    fun updateBeerBusinesses(businesses: Businesses?, isCache: Boolean) {
        mBeerOffset += businesses?.businesses?.size ?: 0
        if (businesses != mUser.beerBusinesses && businesses != null && !isCache) {
            val list = mutableListOf<Business>().apply {
                addAll(businesses.businesses)
                addAll(mUser.beerBusinesses.businesses)
            }
            mUserDao.updateBeerBusinesses(Businesses(businesses.total, list))
        }
    }

    fun updatePizzaBusinesses(businesses: Businesses?, isCache: Boolean) {
        mPizzaOffset += businesses?.businesses?.size ?: 0
        if (businesses != mUser.pizzaBusinesses && businesses != null && !isCache) {
            val list = mutableListOf<Business>().apply {
                addAll(businesses.businesses)
                addAll(mUser.pizzaBusinesses.businesses)
            }
            mUserDao.updatePizzaBusinesses(Businesses(businesses.total, list))
        }
    }

}