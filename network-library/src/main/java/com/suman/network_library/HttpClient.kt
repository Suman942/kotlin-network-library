package com.suman.network_library

import com.suman.network_library.inernal.DownloadRequest

interface HttpClient {

    suspend fun connect(
        downloadRequest: DownloadRequest,
        oonBytes: (bytesRead: Long, totalBytes: Long) -> Unit
    )
}