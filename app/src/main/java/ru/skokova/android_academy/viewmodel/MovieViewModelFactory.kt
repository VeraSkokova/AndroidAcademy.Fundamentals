package ru.skokova.android_academy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.skokova.android_academy.business.MovieDetailsInteractor
import ru.skokova.android_academy.business.MovieListInteractor
import ru.skokova.android_academy.data.JsonMovieDetailsLoader
import ru.skokova.android_academy.data.JsonMovieLoader

class MovieViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MovieListViewModel::class.java -> MovieListViewModel(MovieListInteractor(JsonMovieLoader()))
        MovieDetailsViewModel::class.java -> MovieDetailsViewModel(
            MovieDetailsInteractor(
                JsonMovieDetailsLoader()
            )
        )
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}