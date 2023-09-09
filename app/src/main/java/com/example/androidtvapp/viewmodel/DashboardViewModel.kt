package com.example.androidtvapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidtvapp.Constants
import com.example.androidtvapp.helper.Utils
import com.example.androidtvapp.model.DashBoardDataResponseItem
import com.example.androidtvapp.network.Resource
import com.example.androidtvapp.repository.LEDRepository
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response

class DashboardViewModel(
    application: Application,
    private val ledRepository: LEDRepository
) : AndroidViewModel(application)
{
    private val _dashboardData: MutableLiveData<Resource<List<DashBoardDataResponseItem>?>> = MutableLiveData()
    val dashboardData: MutableLiveData<Resource<List<DashBoardDataResponseItem>?>> = _dashboardData

    fun getDashboardData(showLoader: Boolean) {
        viewModelScope.launch {
            Log.d("ViewModel", "getDashboardData: ")
            getDashboardCountData(showLoader)
        }
    }
    private suspend fun getDashboardCountData(showLoader: Boolean = false , baseUrl: String = Constants.BASE_URL) {
        if(showLoader) {
            _dashboardData.postValue(Resource.Loading())
        }
        try {
            if (Utils.hasInternetConnection(getApplication())) {
                val response = ledRepository.getDashboardData(baseUrl)
                _dashboardData.postValue(handleResponse(response))
            } else {
                _dashboardData.postValue(Resource.Error(Constants.NO_INTERNET))
            }
        } catch (t: Throwable) {
            when (t) {
                is Exception -> {
                    _dashboardData.postValue(Resource.Error("${t.message}"))
                }
                else -> _dashboardData.postValue(Resource.Error(Constants.CONFIG_ERROR))
            }
        }
    }

    private fun handleResponse(response: Response<List<DashBoardDataResponseItem>>): Resource<List<DashBoardDataResponseItem>?>? {
        var errorMessage = ""
        if (response.isSuccessful) {
            response.body()?.let { Response ->
                return Resource.Success(Response)
            }
        } else if (response.errorBody() != null) {
            val errorObject = response.errorBody()?.let {
                JSONObject(it.charStream().readText())
            }
            errorObject?.let {
                errorMessage = it.getString("ErrorMessage")
            }
        }
        return Resource.Error(errorMessage)
    }
}