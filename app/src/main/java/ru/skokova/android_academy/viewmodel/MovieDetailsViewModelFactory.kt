package ru.skokova.android_academy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.skokova.android_academy.business.ActorsInteractor
import ru.skokova.android_academy.business.MovieDetailsInteractor
import ru.skokova.android_academy.data.NetworkActorsRepository
import ru.skokova.android_academy.data.NetworkMovieDetailsRepository
import ru.skokova.android_academy.data.converter.ActorsMapper
import ru.skokova.android_academy.data.converter.MovieMapper

class MovieDetailsViewModelFactory(private val movieId: Int) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MovieDetailsViewModel::class.java -> MovieDetailsViewModel(
            movieId,
            MovieDetailsInteractor(
                NetworkMovieDetailsRepository(MovieMapper())
            ),
            ActorsInteractor(
                NetworkActorsRepository(ActorsMapper())
            )
        )
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}