package com.jewong.turofood.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jewong.turofood.api.data.Businesses
import java.lang.reflect.Type

class DataConverter {

    @TypeConverter
    fun fromBusinesses(businesses: Businesses?): String? {
        val gson = Gson()
        val type: Type = object : TypeToken<Businesses?>() {}.type
        return gson.toJson(businesses, type)
    }

    @TypeConverter
    fun toBusinesses(businesses: String?): Businesses {
        val gson = Gson()
        val type: Type = object : TypeToken<Businesses?>() {}.type
        return gson.fromJson(businesses, type)
    }

}