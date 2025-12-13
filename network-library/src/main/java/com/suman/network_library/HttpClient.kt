package com.suman.network_library

import com.suman.network_library.inernal.DownloadRequest

interface HttpClient {

    fun connect(downloadRequest: DownloadRequest)
}