package ru.skokova.android_academy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PermissionViewModel: ViewModel() {
    private val mutableRequestPermissionLiveData = MutableLiveData<Boolean>()
    val requestPermissionLiveData get() = mutableRequestPermissionLiveData

    private val mutableRationaleShownLiveData = MutableLiveData<Boolean>()
    val rationaleShownLiveData get() = mutableRationaleShownLiveData

    fun onRequestPermission(isGranted: Boolean) {
        mutableRequestPermissionLiveData.value = isGranted
    }

    fun onRationaleShown(isRationaleShown: Boolean) {
        mutableRationaleShownLiveData.value = isRationaleShown
    }
}