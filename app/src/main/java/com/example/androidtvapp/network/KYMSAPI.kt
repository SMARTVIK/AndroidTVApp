package com.example.androidtvapp.network

import com.example.androidtvapp.Constants
import com.example.androidtvapp.model.DashBoardDataResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface KYMSAPI {
    @GET(Constants.DASHBOARD_LED)
    suspend fun getDashboardData(
    ): Response<List<DashBoardDataResponseItem>>
}