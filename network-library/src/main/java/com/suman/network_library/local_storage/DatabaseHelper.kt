package com.suman.network_library.local_storage

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(private val dbHelper: SQLiteOpenHelper) {

    private val writableDb get() = dbHelper.writableDatabase
    fun insert(download: DownloadEntity) {
        val values = ContentValues().apply {
            put("id", download.id)
            put("url", download.url)
            put("dir_path", download.dirPath)
            put("file_name", download.fileName)
            put("downloaded_bytes", download.downloadedBytes)
            put("total_bytes", download.totalBytes)
            put("tag", download.tag)
            put("last_modified", download.lastModified)
            put("status", download.status)
            put("error", download.error)
            put("updated_at", download.updatedAt)
        }
        writableDb.insertWithOnConflict(
            "downloads",
            null,
            values,
            SQLiteDatabase.CONFLICT_REPLACE

        )
    }

    private val readableDb get() = dbHelper.readableDatabase
    fun getDownloadedBytes(id: String): Long {
        val cursor = readableDb.query(
            "downloads",
            arrayOf("downloaded_bytes"),
            "id = ?",
            arrayOf(id),
            null,
            null,
            null
        )

        cursor.use { cursor ->
            return if (cursor.moveToFirst()) cursor.getLong(0) else 0L
        }
    }

    fun updateProgress(id: String, downloadedBytes: Long, status: Int? = null) {
        val values = ContentValues().apply {
            put("downloaded_bytes", downloadedBytes)
            status?.let {
                put("status", it)
            }
            put("updated_at", System.currentTimeMillis())
        }
        writableDb.update(
            "downloads",
            values,
            "id=?",
            arrayOf(id)
        )

    }

    fun deleteDownload(id: String){
        writableDb.delete(
            "downloads",
            "id=?",
            arrayOf(id)
        )
    }

}