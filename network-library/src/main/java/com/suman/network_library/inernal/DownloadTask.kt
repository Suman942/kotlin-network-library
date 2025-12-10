package com.suman.network_library.inernal

import com.suman.network_library.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class DownloadTask(private val downloadRequest: DownloadRequest,private val httpClient: HttpClient) {

    suspend fun run(
        onStart:()-> Unit ={},
        onPause:()-> Unit ={},
        onCompleted:() -> Unit ={},
        onProgress:(value: Int)-> Unit={_,->},
        onError:(error:String)-> Unit = {_,->}
    ){
        withContext(Dispatchers.IO) {
            // dummy code for downloading the file
            onStart()

            // use of http client
            httpClient.connect()

            // stimulate read data from internet
            for (i in 1..100) {
                delay(100)
                onProgress(i)
            }

            onCompleted()
        }
    }
}