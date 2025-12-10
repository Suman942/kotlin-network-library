package com.suman.network_library.inernal

class DownloadRequestQueue(private val dispatchers: DownloadDispatchers) {

    private val idRequestMap: HashMap<Int, DownloadRequest> = hashMapOf()

    fun enqueue(downloadRequest: DownloadRequest): Int{
        idRequestMap[downloadRequest.downloadId] = downloadRequest
        return dispatchers.enqueue(downloadRequest)
    }
}