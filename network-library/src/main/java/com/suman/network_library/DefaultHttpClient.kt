package com.suman.network_library

import android.util.Log
import com.suman.network_library.inernal.DownloadRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.coroutineContext


class DefaultHttpClient : HttpClient {

    override suspend fun connect(
        downloadRequest: DownloadRequest,
        onBytes: (bytesRead: Long, totalBytes: Long) -> Unit
    ) = withContext(Dispatchers.IO) {
        var connection: HttpURLConnection? = null
        try {
            val url = URL(downloadRequest.url)
            connection = (url.openConnection() as HttpURLConnection).apply {
                connectTimeout = downloadRequest.connectTimeOut
                readTimeout = downloadRequest.readTimeOut
                requestMethod = "GET"
                doInput = true
                connect()
            }
            val totalBytes = connection.contentLength
            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                throw Exception("HTTP error code: ${connection.responseCode}")
            }
            val file = File(downloadRequest.dirPath, downloadRequest.fileName)
            Log.d("DownloadProgress","file length: ${file.length()}")
            connection.inputStream.use { inputStream ->
                file.outputStream().use { outputStream ->
                    copyInputStreamProgress(inputStream, outputStream, totalBytes.toLong(), onBytes)
                }
            }
        } finally {
            connection?.disconnect()
        }

    }

    private suspend fun copyInputStreamProgress(
        input: InputStream,
        output: OutputStream,
        totalBytes: Long,
        onBytes: (Long, Long) -> Unit
    ) {
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        var bytesRead: Int
        var downloadedBytes = 0L

        while (true) {
            coroutineContext.ensureActive()
            bytesRead = input.read(buffer)
            if (bytesRead == -1) break

            output.write(buffer, 0, bytesRead)
            downloadedBytes += bytesRead

            // Debug logs to help trace issues
            Log.d("DownloadProgress","Read $bytesRead bytes, total downloaded: $downloadedBytes / $totalBytes")

            onBytes(downloadedBytes, totalBytes)
        }
    }


//    private suspend fun copyInputStreamProgress(
//        input: InputStream,
//        output: OutputStream,
//        totalBytes: Long,
//        onBytes: (Long, Long) -> Unit
//    ) {
//        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
//        var bytesRead: Int
//        var downloadedBytes = 0L
//
//        while (input.read(buffer).also { bytesRead = it } != -1) {
//            coroutineContext.ensureActive()
//            output.write(buffer, 0, bytesRead)
//            downloadedBytes += bytesRead
//            onBytes(downloadedBytes, totalBytes)
//        }
//    }
}