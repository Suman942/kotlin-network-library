package com.suman.network_library

import com.suman.network_library.inernal.DownloadRequest

class Downloader private constructor(private val downloaderConfig: DownloaderConfig) {

    companion object{
        fun create(downloaderConfig: DownloaderConfig = DownloaderConfig()): Downloader{
            return Downloader(downloaderConfig)
        }
    }

//    private val requestQueue = DownLoad
    fun newReqBuilder(url: String,dirPath: String,fileName: String) : DownloadRequest.Builder{
        return DownloadRequest.Builder(url,dirPath,fileName)
            .setReadTimeOut(downloaderConfig.readTimeOut)
            .setConnectTimeOut(downloaderConfig.connectionTimeOut)
    }

    fun enqueue(
        request: DownloadRequest,
        onStart: ()-> Unit = {},
        onPause:() -> Unit = {},
        onProgress:(value : Int)-> Unit = {_,->},
        onError:(error: String)-> Unit= {_,->},
        onComplete:() -> Unit = {}

    ):Int{
        request.onStart = onStart
        request.onPause = onPause
        request.onProgress = onProgress
        request.onComplete = onComplete
        request.onError = onError
        return 0
    }
}