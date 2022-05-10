package com.example.artapp.data.response.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.artapp.data.response.ArtItem
import com.example.artapp.data.response.SavedItem

data class reposandsaved (

    @Embedded val artItem: ArtItem,
    @Relation(
        parentColumn = "guid",
        entityColumn = "guid"
    )
    val savedItem: SavedItem

        )