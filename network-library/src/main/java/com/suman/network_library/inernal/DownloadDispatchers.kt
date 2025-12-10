package com.suman.network_library.inernal

import com.suman.network_library.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class DownloadDispatchers(private val httpClient: HttpClient) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    fun enqueue(downloadReq: DownloadRequest): Int{
        val job = scope.launch {
        execute(downloadReq)
        }
        downloadReq.job
        return downloadReq.downloadId
    }

    private suspend fun execute(downloadReq: DownloadRequest){
        DownloadTask(downloadReq,httpClient).run(
            onStart = {},
            onProgress = {},
            onPause = {},
            onError = {},
            onCompleted = {}
        )
    }
}