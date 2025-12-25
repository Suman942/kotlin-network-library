package com.suman.kotlin_network_library

import android.app.Application
import com.suman.network_library.Downloader

class MyApplication: Application() {
    lateinit var downloader:Downloader

    override fun onCreate() {
        super.onCreate()
        downloader= Downloader.create(this)
    }
}