package com.suman.kotlin_network_library

import android.os.Bundle
import android.os.Environment
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.suman.kotlin_network_library.databinding.ActivityMainBinding
import com.suman.network_library.Constants
import com.suman.network_library.Downloader
import com.suman.network_library.inernal.DownloadRequest

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var downloader: Downloader
    private lateinit var request: DownloadRequest
    val url = "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEivjcWPthi-WHxcwoy7tZK8O4CVv66U55HhVtEzQJedml2pY3xEjX-C8CbtSiB-vZywGNEl05lAXSxkfoo5WBNfXtxabZ2RRNs8vD0IBDoCQfLKBmSaZTkYC8DseoyeklNgP1n8ffvodiQocbhP7Epjpgb162Ydn5lmyNE3PUVJq7l_pjkYB5rtMLbSwqI/s1600/theandroidshow-google-io-2025.png"
    val downloadsPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

         downloader = (application as MyApplication).downloader

        binding.apply {
            btn.setOnClickListener {
               download()
            }
        }





    }

    private fun download(){
        request = downloader.newReqBuilder(url,
            dirPath = downloadsPath,
            fileName = "$packageName _${System.currentTimeMillis()}")
            .readTimeOut(Constants.DEFAULT_READ_TIMEOUT_MILLIS)
            .connectTimeOut(Constants.DEFAULT_CONNECT_TIMEOUT_MILLIS)
            .setTag("someTag")
            .build()

        downloader.enqueue(
            request,
            onStart = {
                binding.textViewStatus.text = "onStart"
            },
            onProgress = {
                binding.textViewProgress.text = "$it %"
                binding.progressBar.progress = it
            },
            onPause = {
                binding.textViewStatus.text = "onPause"
            },
            onComplete = {
                binding.textViewStatus.text = "onCompleted \n${downloadsPath}"

            },
            onError = {
                binding.textViewStatus.text = it
            })

    }
}