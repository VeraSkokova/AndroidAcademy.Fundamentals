package ru.skokova.android_academy.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.skokova.android_academy.business.ImageLoadingInfoInteractor
import ru.skokova.android_academy.data.Resource

class ImageLoadingInfoViewModel(private val imageApiDetailsInteractorInteractor: ImageLoadingInfoInteractor) :
    ViewModel() {
    private val mutableConfigurationLiveData: MutableLiveData<Resource<ru.skokova.android_academy.data.model.ImageLoadingInfo>> =
        MutableLiveData()
    val configurationLiveData: LiveData<Resource<ru.skokova.android_academy.data.model.ImageLoadingInfo>> get() = mutableConfigurationLiveData

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        mutableConfigurationLiveData.value =
            Resource.Error(exception.message ?: "Error in loading configuration")
        Log.e(ERROR_TAG, "CoroutineExceptionHandler got $exception in $coroutineContext")
    }

    init {
        viewModelScope.launch(exceptionHandler) {
            val configuration = imageApiDetailsInteractorInteractor.getImageLoadingInfo()
            mutableConfigurationLiveData.value = Resource.Success(configuration)
        }
    }

    companion object {
        const val ERROR_TAG = "COROUTINE_ERROR"
    }
}