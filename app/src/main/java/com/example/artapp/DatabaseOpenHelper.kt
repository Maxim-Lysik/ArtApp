package com.example.artapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

class DatabaseOpenHelper(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int,
) : SQLiteAssetHelper(context, name, factory, version) {

    private val DATABASE_NAME = "retrodatabase.db"
    private val DATABASE_VERSION = 1



}