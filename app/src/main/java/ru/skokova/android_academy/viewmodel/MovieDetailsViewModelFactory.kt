package ru.skokova.android_academy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.skokova.android_academy.business.MovieDetailsInteractor
import ru.skokova.android_academy.data.DbActorsRepository
import ru.skokova.android_academy.data.DbMovieDetailsRepository
import ru.skokova.android_academy.data.NetworkActorsRepository
import ru.skokova.android_academy.data.NetworkMovieDetailsRepository
import ru.skokova.android_academy.data.mapper.EntityActorsMapper
import ru.skokova.android_academy.data.mapper.EntityMovieMapper
import ru.skokova.android_academy.data.mapper.JsonActorsMapper
import ru.skokova.android_academy.data.mapper.JsonMovieMapper
import ru.skokova.android_academy.data.network.RetrofitModule

class MovieDetailsViewModelFactory(private val movieId: Int) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MovieDetailsViewModel::class.java -> MovieDetailsViewModel(
            movieId,
            MovieDetailsInteractor(
                NetworkMovieDetailsRepository(RetrofitModule.moviesApi, JsonMovieMapper()),
                DbMovieDetailsRepository(EntityMovieMapper()),
                NetworkActorsRepository(RetrofitModule.moviesApi, JsonActorsMapper()),
                DbActorsRepository(EntityActorsMapper())
            )
        )
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}