package com.example.artapp

import RetroRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artapp.data.response.ArtItem
import com.example.artapp.data.response.GeoCoordinates
import com.example.artapp.data.response.SavedItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapsViewModel : ViewModel() {

    var coordinates_mod = ""
    val coordinates_mod2 = ""


    private val retroRepository = RetroRepository.get()

    fun saveArtItem(item: ArtItem) {
        retroRepository.updateArtItem(item)
    }

    fun insertArtItem(item: ArtItem) {
        retroRepository.addArtItem(item)
    }

    fun itemByGeo(geoCoordinates: GeoCoordinates): LiveData<ArtItem?> {
        return retroRepository.getArtItem(geoCoordinates)
    }

    fun updateitem(item: ArtItem) {
        retroRepository.updateArtItem(item)
    }

    fun getAlliteems() {
        viewModelScope.launch(Dispatchers.IO) {
            val artItemsLiveData = retroRepository.getArtItems()

        }
    }

    val our_artitems = retroRepository.getArtItems()

    fun itemsByCity(city: String): LiveData<List<ArtItem>> {
        return retroRepository.getArtItemsByCity(city)
    }

    fun deleteAllItems() = retroRepository.deleteItems()


    // Methods for SavedBD

    fun saveSaveItem(item: SavedItem) {
        retroRepository.updateSaveItem(item)
    }

    fun insertSaveItem(item: SavedItem) {
        retroRepository.addSaveItem(item)
    }

    fun insertArtItemtoSaveItem(item: ArtItem) {
        retroRepository.addArtItemtoSaved(item)
    }

    fun saveitemByGeo(geoCoordinates: GeoCoordinates): LiveData<SavedItem?> {
        return retroRepository.getSaveItem(geoCoordinates)
    }

    fun getAllSaveiteems() {
        viewModelScope.launch(Dispatchers.IO) {
            val artItemsLiveData = retroRepository.getSavedItems()

        }
    }

    val our_saveditems = retroRepository.getSavedItems()

    fun deletebyguid(guid: String) = retroRepository.deletebyguid(guid)

    fun deleteSavedItems() = retroRepository.deleteSaveItems()


}