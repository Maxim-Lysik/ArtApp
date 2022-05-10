package com.example.artapp.data.response

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*


data class GeoCoordinates(
    val lat: String,
    val lon: String,
)

class TypeConverterOwner {
    val gson: Gson = Gson()

    @TypeConverter
    fun stringtoSomeObjectList(data: String?): GeoCoordinates? {
        if (data == null) return null
        val listType: Type = object : TypeToken<GeoCoordinates?>() {}.type
        return gson.fromJson<GeoCoordinates>(data, listType)
    }


    @TypeConverter
    fun someObjectListToString(someobject: GeoCoordinates?): String? {
        return gson.toJson(someobject)
    }


    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }


}


