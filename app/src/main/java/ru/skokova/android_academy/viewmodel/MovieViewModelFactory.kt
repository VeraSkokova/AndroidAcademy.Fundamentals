package ru.skokova.android_academy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.skokova.android_academy.MoviesApplication
import ru.skokova.android_academy.business.ImageLoadingInfoInteractor
import ru.skokova.android_academy.business.MovieListInteractor
import ru.skokova.android_academy.data.*
import ru.skokova.android_academy.data.mapper.*
import ru.skokova.android_academy.data.network.RetrofitModule

class MovieViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MovieListViewModel::class.java -> MovieListViewModel(
            MovieListInteractor(
                NetworkMovieListRepository(
                    RetrofitModule.moviesApi,
                    JsonMovieMapper(),
                    DbGenresRepository(EntityGenreMapper())
                ),
                DbMovieListRepository(EntityMovieMapper()),
                NetworkGenresRepository(RetrofitModule.moviesApi, JsonGenreMapper()),
                DbGenresRepository(EntityGenreMapper()),
                NotificationsHelper(MoviesApplication.context)
            )
        )
        ImageLoadingInfoViewModel::class.java -> ImageLoadingInfoViewModel(
            ImageLoadingInfoInteractor(
                NetworkImageLoadingInfoRepository(
                    RetrofitModule.moviesApi,
                    JsonImageLoadingInfoMapper()
                ),
                CachedImageLoadingInfoRepository()
            )
        )
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}