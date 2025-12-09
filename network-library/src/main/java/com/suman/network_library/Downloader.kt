package com.suman.network_library

class Downloader private constructor(private val downloaderConfig: DownloaderConfig) {

    companion object{
        fun create(downloaderConfig: DownloaderConfig = DownloaderConfig()): Downloader{
            return Downloader(downloaderConfig)
        }
    }

    fun newReqBuilder(url: String,dirPath: String,fileName: String) :
}