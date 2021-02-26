package com.jewong.turofood.api.core

interface TFApiCallback<T> {

    fun onResponse(response: T?)

    fun onFailure(throwable: Throwable)

}