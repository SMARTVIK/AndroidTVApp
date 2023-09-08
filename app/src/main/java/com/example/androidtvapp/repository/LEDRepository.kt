package com.example.androidtvapp.repository

import com.kemarport.mahindrakiosk.api.RetrofitInstance

class LEDRepository {
    suspend fun getDashboardData(
        baseUrl: String
    ) = RetrofitInstance.api(baseUrl).getDashboardData()
}