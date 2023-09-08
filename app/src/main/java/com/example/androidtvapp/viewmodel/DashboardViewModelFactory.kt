package com.example.androidtvapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidtvapp.repository.LEDRepository

class DashboardViewModelFactory(
    private val application: Application,
    private val ledRepository: LEDRepository
) : ViewModelProvider.Factory   {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DashboardViewModel(application, ledRepository) as T
    }
}