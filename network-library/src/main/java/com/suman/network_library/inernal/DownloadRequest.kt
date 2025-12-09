package com.suman.network_library.inernal

class DownloadRequest private constructor(
    internal val url: String,
    internal val tag: String?,
    internal val dirPath:String,
    internal val downloadId:String,
    internal val fileName:String,
    internal val readTimeOut: Int,
    internal val connectTimeOut: Int
    )
