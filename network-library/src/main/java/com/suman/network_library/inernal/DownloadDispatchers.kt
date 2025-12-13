package com.suman.network_library.inernal

import com.suman.network_library.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class DownloadDispatchers(private val httpClient: HttpClient) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    fun enqueue(downloadReq: DownloadRequest): Int{
        val job = scope.launch {
        execute(downloadReq)
        }
        downloadReq.job = job
        return downloadReq.downloadId
    }

    private suspend fun execute(downloadReq: DownloadRequest){
        DownloadTask(downloadReq,httpClient).run(
            onStart = {
                executeOnMainThread { downloadReq.onStart()}
            },
            onProgress = {executeOnMainThread { downloadReq.onProgress(it)}},
            onPause = { executeOnMainThread { downloadReq.onPause}},
            onError = { executeOnMainThread {downloadReq.onError}},
            onCompleted = {executeOnMainThread {downloadReq.onComplete}}
        )
    }

    private fun executeOnMainThread(block: () -> Unit) {
        scope.launch {
            block()
        }
    }

    fun cancel(request: DownloadRequest){
        request.job.cancel()
    }

    fun cancelAll(){
        scope.cancel()
    }
}