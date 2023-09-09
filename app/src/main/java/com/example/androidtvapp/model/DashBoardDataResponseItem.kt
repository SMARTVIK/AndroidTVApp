package com.example.androidtvapp.model

data class DashBoardDataResponseItem(
    val assemblyLine: String,
    val lineStatus: String,
    val skuCode: String,
    val actual: String,
    val target: String,
    val totalCount: Int
)