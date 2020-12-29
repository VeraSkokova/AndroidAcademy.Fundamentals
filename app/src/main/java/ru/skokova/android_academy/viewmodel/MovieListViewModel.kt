package ru.skokova.android_academy.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.skokova.android_academy.business.MovieListInteractor
import ru.skokova.android_academy.data.Movie
import ru.skokova.android_academy.data.Resource

class MovieListViewModel(private val movieListInteractor: MovieListInteractor) : ViewModel() {
    private val mutableMoviesLiveData = MutableLiveData<Resource<List<Movie>>>()
    val moviesLiveData: LiveData<Resource<List<Movie>>> get() = mutableMoviesLiveData

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        mutableMoviesLiveData.value = Resource.Error(exception.message ?: DEFAULT_ERROR)
        Log.e(ERROR_TAG, "CoroutineExceptionHandler got $exception in $coroutineContext")
    }

    fun getMovies(): LiveData<Resource<List<Movie>>> {
        if (mutableMoviesLiveData.value == null) {
            viewModelScope.launch(exceptionHandler) {
                val movies = movieListInteractor.getMovies()
                mutableMoviesLiveData.value = Resource.Success(movies)
            }
        }
        return moviesLiveData
    }

    companion object {
        private const val ERROR_TAG = "COROUTINE_ERROR"
        private const val DEFAULT_ERROR = "An error occurred while loading movie list"
    }
}