package com.example.artapp.data.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repositories")
data class ArtItem(

    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "cityName") val cityName: String,
    @ColumnInfo(name = "description") val countryCode: String,
    @ColumnInfo(name = "countryName") val countryName: String,
    @ColumnInfo(name = "createdYear") val createdYear: String,
    @ColumnInfo(name = "geoCoordinates") val geoCoordinates: GeoCoordinates,
    @PrimaryKey val guid: String,
    @ColumnInfo(name = "linkOpenStreetMap") val linkOpenStreetMap: String,
    @ColumnInfo(name = "pictLink") var pictLink: String,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "writersNames") val writersNames: String,
)