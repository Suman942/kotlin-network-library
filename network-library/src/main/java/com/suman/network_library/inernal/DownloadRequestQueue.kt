package com.suman.network_library.inernal

class DownloadRequestQueue(private val dispatchers: DownloadDispatchers) {

    private val idRequestMap: HashMap<Int, DownloadRequest> = hashMapOf()

    fun enqueue(downloadRequest: DownloadRequest): Int{
        idRequestMap[downloadRequest.downloadId] = downloadRequest
        return dispatchers.enqueue(downloadRequest)
    }

    fun pause(id: Int){
        idRequestMap[id]?.let {
            dispatchers.cancel(it)
        }
    }

    fun resume(id: Int){
        idRequestMap[id]?.let {
            dispatchers.enqueue(it)
        }
    }

    fun cancel(id: Int){
        idRequestMap[id]?.let {
            it.onCancel.invoke()
            dispatchers.cancel(it)
        }
        idRequestMap.remove(id)

    }


}