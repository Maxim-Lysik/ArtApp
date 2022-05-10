package com.example.artapp.toroom

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.artapp.data.response.ArtItem
import com.example.artapp.data.response.GeoCoordinates

@Dao
interface AppDao {

    @Query("SELECT * FROM repositories")
    fun getAllRecords(): LiveData<List<ArtItem>>

    @Query("SELECT * FROM repositories WHERE geoCoordinates =(:geoCoordinates)")
    fun getArtItem(geoCoordinates: GeoCoordinates): LiveData<ArtItem?>

    @Query("SELECT * FROM repositories WHERE cityName =(:cityName)")
    fun getArtItemsByCity(cityName: String): LiveData<List<ArtItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItem(vararg users: ArtItem)

    @Update
    fun updateItems(item: ArtItem)

    @Query("DELETE FROM repositories")
    fun deleteAllRecords()

}