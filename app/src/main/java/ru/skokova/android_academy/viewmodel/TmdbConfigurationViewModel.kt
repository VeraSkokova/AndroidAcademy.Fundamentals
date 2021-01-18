package ru.skokova.android_academy.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.skokova.android_academy.business.ConfigurationInteractor
import ru.skokova.android_academy.data.Resource
import ru.skokova.android_academy.data.TmdbConfiguration

class TmdbConfigurationViewModel(private val configurationInteractor: ConfigurationInteractor) :
    ViewModel() {
    private val mutableConfigurationLiveData: MutableLiveData<Resource<TmdbConfiguration>> =
        MutableLiveData()
    val configurationLiveData: LiveData<Resource<TmdbConfiguration>> get() = mutableConfigurationLiveData

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        mutableConfigurationLiveData.value =
            Resource.Error(exception.message ?: "Error in loading configuration")
        Log.e(ERROR_TAG, "CoroutineExceptionHandler got $exception in $coroutineContext")
    }

    init {
        viewModelScope.launch(exceptionHandler) {
            val configuration = configurationInteractor.getConfiguration()
            mutableConfigurationLiveData.value = Resource.Success(configuration)
        }
    }

    companion object {
        const val ERROR_TAG = "COROUTINE_ERROR"
    }
}