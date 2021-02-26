package com.jewong.turofood.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jewong.turofood.api.data.Businesses

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    var userId: Long = 0L,
    val pizzaBusinesses: Businesses = Businesses(),
    val beerBusinesses: Businesses = Businesses()

)