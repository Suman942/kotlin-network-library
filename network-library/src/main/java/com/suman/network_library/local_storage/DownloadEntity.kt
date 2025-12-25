package com.suman.network_library.local_storage

data class DownloadEntity(
    val id: String,
    val url: String,
    val dirPath: String,
    val fileName: String,
    val downloadedBytes: Long,
    val totalBytes: Long?,
    val tag: String?,
    val lastModified: String?,
    val status: Int,
    val error: String?,
    val updatedAt: Long
)
