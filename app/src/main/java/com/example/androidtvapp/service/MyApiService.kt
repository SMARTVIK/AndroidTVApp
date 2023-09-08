package com.example.androidtvapp.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import androidx.lifecycle.ViewModelProvider
import com.example.androidtvapp.network.Resource
import com.example.androidtvapp.repository.LEDRepository
import com.example.androidtvapp.viewmodel.DashboardViewModel
import com.example.androidtvapp.viewmodel.DashboardViewModelFactory

class MyApiService : Service() {
    private val handler = Handler()
    private val apiRunnable = object : Runnable {
        override fun run() {
            // Perform API request here
            // Replace this with your API request logic
            // Example: fetchDataFromApi()
            handler.postDelayed(this, 60_000) // Run every 1 minute (60,000 milliseconds)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handler.post(apiRunnable)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(apiRunnable)
    }
}
