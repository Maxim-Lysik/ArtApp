package com.example.artapp.toroom

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.artapp.data.response.ArtItem
import com.example.artapp.data.response.GeoCoordinates
import com.example.artapp.data.response.SavedItem

@Dao
interface SavedItemsDao {


    @Query("SELECT * FROM saveditems")
    fun getAllRecords(): LiveData<List<SavedItem>>

    @Query("SELECT * FROM saveditems WHERE geoCoordinates =(:geoCoordinates)")
    fun getArtItem(geoCoordinates: GeoCoordinates): LiveData<SavedItem?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItem(vararg users: SavedItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addArtItemtoSaved(vararg users: ArtItem)

    @Update
    fun updateItems(item: SavedItem)

    @Query("DELETE FROM saveditems")
    fun deleteAllRecords()

    @Query("DELETE FROM saveditems WHERE guid = :guId")
    fun deleteByGuId(guId: String)


}