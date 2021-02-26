package com.jewong.turofood.api

interface TFApiCallback<T> {

    fun onResponse(response: T?)

    fun onFailure(throwable: Throwable)

}