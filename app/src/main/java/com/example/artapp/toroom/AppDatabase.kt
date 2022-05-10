package com.example.artapp.toroom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.artapp.data.response.ArtItem
import com.example.artapp.data.response.SavedItem
import com.example.artapp.data.response.TypeConverterOwner


@Database(entities = [ArtItem::class, SavedItem::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverterOwner::class)

abstract class AppDatabase : RoomDatabase() {

    abstract fun getAppDao(): AppDao
    abstract fun getSavedDao(): SavedItemsDao

    companion object {

        private var DB_INSTANCE: AppDatabase? = null

        fun getAppDBInstance(context: Context): AppDatabase {

            if (DB_INSTANCE == null) {
              DB_INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "APP_DB")
                  .allowMainThreadQueries()
                  .fallbackToDestructiveMigration()
                  .build()
            }
            return DB_INSTANCE!!
        }
    }

}