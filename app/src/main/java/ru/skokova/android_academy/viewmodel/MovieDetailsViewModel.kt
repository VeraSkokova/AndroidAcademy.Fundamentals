package ru.skokova.android_academy.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.skokova.android_academy.business.MovieDetailsInteractor
import ru.skokova.android_academy.data.Movie
import ru.skokova.android_academy.data.Resource

class MovieDetailsViewModel(private val movieDetailsInteractor: MovieDetailsInteractor) :
    ViewModel() {
    private val mutableMovieDetailsLiveData = MutableLiveData<Resource<Movie>>()
    val movieDetailsLiveData: LiveData<Resource<Movie>> get() = mutableMovieDetailsLiveData

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        mutableMovieDetailsLiveData.value = Resource.Error(exception.message ?: DEFAULT_ERROR)
        Log.e(ERROR_TAG, "CoroutineExceptionHandler got $exception in $coroutineContext")
    }

    fun getMovieDetails(id: Int) {
        viewModelScope.launch(exceptionHandler) {
            val movie = movieDetailsInteractor.getMovieById(id)
            mutableMovieDetailsLiveData.value = movie?.let {
                Resource.Success(movie)
            } ?: Resource.Error(NULL_MESSAGE)
        }
    }

    companion object {
        private const val ERROR_TAG = "COROUTINE_ERROR"
        private const val DEFAULT_ERROR = "An error occurred while loading movie details"
        private const val NULL_MESSAGE = "There is no movie with this id"
    }
}