package ru.skokova.android_academy.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.skokova.android_academy.business.ActorsInteractor
import ru.skokova.android_academy.data.Resource
import ru.skokova.android_academy.data.model.Actor

class ActorsViewModel(private val actorsInteractor: ActorsInteractor) : ViewModel() {
    private val mutableActorsLiveData = MutableLiveData<Resource<List<Actor>>>()
    val actorsLiveData: LiveData<Resource<List<Actor>>> get() = mutableActorsLiveData

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        mutableActorsLiveData.value = Resource.Error(
            exception.message ?: "An error occurred while loading movie details. Please, check logs"
        )
        Log.e(ERROR_TAG, "CoroutineExceptionHandler got $exception in $coroutineContext")
    }

    fun getMovieActors(movieId: Int) {
        viewModelScope.launch(exceptionHandler) {
            val movie = actorsInteractor.getMovieActors(movieId)
            mutableActorsLiveData.value = Resource.Success(movie)
        }
    }

    companion object {
        private const val ERROR_TAG = "COROUTINE_ERROR"
    }
}