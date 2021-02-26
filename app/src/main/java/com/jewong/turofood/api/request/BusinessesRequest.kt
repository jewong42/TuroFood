package com.jewong.turopizzaapp.api.request

data class BusinessesRequest(

    val term: String,
    val latitude: String,
    val longitude: String
)