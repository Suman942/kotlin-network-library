package com.suman.network_library

import com.suman.network_library.inernal.DownloadDispatchers
import com.suman.network_library.inernal.DownloadRequest
import com.suman.network_library.inernal.DownloadRequestQueue

class Downloader private constructor(private val downloaderConfig: DownloaderConfig) {

    companion object{
        fun create(downloaderConfig: DownloaderConfig = DownloaderConfig()): Downloader{
            return Downloader(downloaderConfig)
        }
    }

    private val requestQueue = DownloadRequestQueue(DownloadDispatchers(downloaderConfig.httpClient))
    fun newReqBuilder(url: String,dirPath: String,fileName: String) : DownloadRequest.Builder{
        return DownloadRequest.Builder(url,dirPath,fileName)
            .readTimeOut(downloaderConfig.readTimeOut)
            .connectTimeOut(downloaderConfig.connectionTimeOut)
    }

    fun enqueue(
        request: DownloadRequest,
        onStart: ()-> Unit = {},
        onPause:() -> Unit = {},
        onProgress:(value : Int)-> Unit = {_,->},
        onError:(error: String)-> Unit= {_,->},
        onCancel:()-> Unit = {},
        onComplete:() -> Unit = {},
        onResume:(value : Long) -> Unit = {_,->}

    ):Int{
        request.onStart = onStart
        request.onPause = onPause
        request.onProgress = onProgress
        request.onComplete = onComplete
        request.onError = onError
        request.onCancel = onCancel
        request.onResume = onResume
        return requestQueue.enqueue(request)
    }

    fun cancel(id: Int){
        requestQueue.cancel(id)
    }

    fun pause(id: Int){
        requestQueue.pause(id)
    }

    fun resume(id: Int){
        requestQueue.resume(id)
    }
}