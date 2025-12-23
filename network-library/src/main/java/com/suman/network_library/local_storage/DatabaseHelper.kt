package com.suman.network_library.local_storage

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(private val dbHelper: SQLiteOpenHelper) {

    private val writableDatabase: SQLiteDatabase
        get() = dbHelper.writableDatabase

    fun insert() {
        val db = writableDatabase

    }

}