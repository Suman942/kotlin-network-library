package com.suman.kotlin_network_library

import android.database.DatabaseUtils
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.suman.kotlin_network_library.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

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

        val downloader = (application as MyApplication).downloader

        val request = downloader.newReqBuilder("someUrl", "someDirPath", "someFileName")
            .readTimeOut(10000)
            .connectTimeOut(10000)
            .setTag("someTag")
            .build()

        downloader.enqueue(request,
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
                binding.textViewStatus.text = "onCompleted"
            },
            onError = {
                binding.textViewStatus.text = it
            })

    }
}