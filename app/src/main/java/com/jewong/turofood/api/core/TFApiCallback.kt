package com.jewong.turofood.api.core

interface TFApiCallback<T> {

    fun onResponse(response: T?, isCache: Boolean)

    fun onFailure(throwable: Throwable)

}