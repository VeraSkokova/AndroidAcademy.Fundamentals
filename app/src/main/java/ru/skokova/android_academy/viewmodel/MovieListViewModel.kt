package ru.skokova.android_academy.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.skokova.android_academy.business.MovieListInteractor
import ru.skokova.android_academy.data.Resource
import ru.skokova.android_academy.data.model.Movie

class MovieListViewModel(
    private val movieListInteractor: MovieListInteractor
) : ViewModel() {
    private val mutableMoviesLiveData = MutableLiveData<Resource<List<Movie>>>()
    val moviesLiveData: LiveData<Resource<List<Movie>>> get() = mutableMoviesLiveData

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        mutableMoviesLiveData.value = Resource.Error(exception.message ?: DEFAULT_ERROR)
        Log.e(ERROR_TAG, "CoroutineExceptionHandler got $exception in $coroutineContext")
    }

    init {
        viewModelScope.launch(exceptionHandler) {
            mutableMoviesLiveData.value = Resource.Loading()
            val loadedMovies = movieListInteractor.getMovies()
            mutableMoviesLiveData.value = Resource.Success(loadedMovies)
        }
    }

    companion object {
        private const val ERROR_TAG = "COROUTINE_ERROR"
        private const val DEFAULT_ERROR = "An error occurred while loading movie list"
    }
}