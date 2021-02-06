package ru.skokova.android_academy.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.skokova.android_academy.business.ActorsInteractor
import ru.skokova.android_academy.business.MovieDetailsInteractor
import ru.skokova.android_academy.data.Resource
import ru.skokova.android_academy.data.model.MovieDetails

class MovieDetailsViewModel(
    movieId: Int,
    private val movieDetailsInteractor: MovieDetailsInteractor,
    private val actorsInteractor: ActorsInteractor
) :
    ViewModel() {
    private val mutableMovieDetailsLiveData = MutableLiveData<Resource<MovieDetails>>()
    val movieDetailsLiveData: LiveData<Resource<MovieDetails>> get() = mutableMovieDetailsLiveData

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        if (!ViewModelUtils.isNetworkException(exception)) {
            mutableMovieDetailsLiveData.value = Resource.Error(exception.message ?: DEFAULT_ERROR)
        }
        Log.e(ERROR_TAG, "CoroutineExceptionHandler got $exception in $coroutineContext")
    }

    init {
        getMovieDetails(movieId)
    }

    private fun getMovieDetails(id: Int) {
        viewModelScope.launch(exceptionHandler) {
            val cachedMovie = movieDetailsInteractor.getCachedMovie(id)
            val cachedActors = actorsInteractor.getCachedMovieActors(id)
            mutableMovieDetailsLiveData.value =
                Resource.Success(MovieDetails(cachedMovie, cachedActors))

            val loadedMovie = movieDetailsInteractor.getMovieById(id)
            val loadedActors = actorsInteractor.getMovieActors(id)
            mutableMovieDetailsLiveData.value =
                Resource.Success(MovieDetails(loadedMovie, loadedActors))
        }
    }

    companion object {
        private const val ERROR_TAG = "COROUTINE_ERROR"
        private const val DEFAULT_ERROR = "An error occurred while loading movie details"
    }
}