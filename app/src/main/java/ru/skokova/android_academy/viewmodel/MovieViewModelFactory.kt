package ru.skokova.android_academy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.skokova.android_academy.business.ActorsInteractor
import ru.skokova.android_academy.business.ConfigurationInteractor
import ru.skokova.android_academy.business.MovieDetailsInteractor
import ru.skokova.android_academy.business.MovieListInteractor
import ru.skokova.android_academy.data.*

class MovieViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MovieListViewModel::class.java -> MovieListViewModel(
            MovieListInteractor(
                NetworkMovieLoader(),
                GenresLoader()
            )
        )
        MovieDetailsViewModel::class.java -> MovieDetailsViewModel(
            MovieDetailsInteractor(
                NetworkMovieDetailsLoader()
            )
        )
        TmdbConfigurationViewModel::class.java -> TmdbConfigurationViewModel(
            ConfigurationInteractor(
                ConfigurationLoader()
            )
        )
        ActorsViewModel::class.java -> ActorsViewModel(ActorsInteractor(ActorsLoader()))
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}