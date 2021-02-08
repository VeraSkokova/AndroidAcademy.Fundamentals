package ru.skokova.android_academy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.skokova.android_academy.business.ImageLoadingInfoInteractor
import ru.skokova.android_academy.business.MovieListInteractor
import ru.skokova.android_academy.data.CachedImageLoadingInfoRepository
import ru.skokova.android_academy.data.NetworkGenresRepository
import ru.skokova.android_academy.data.NetworkImageLoadingInfoRepository
import ru.skokova.android_academy.data.NetworkMovieListRepository
import ru.skokova.android_academy.data.converter.GenreMapper
import ru.skokova.android_academy.data.converter.ImageLoadingInfoMapper
import ru.skokova.android_academy.data.converter.MovieMapper

class MovieViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MovieListViewModel::class.java -> MovieListViewModel(
            MovieListInteractor(
                NetworkMovieListRepository(MovieMapper()),
                NetworkGenresRepository(GenreMapper())
            )
        )
        ImageLoadingInfoViewModel::class.java -> ImageLoadingInfoViewModel(
            ImageLoadingInfoInteractor(
                NetworkImageLoadingInfoRepository(ImageLoadingInfoMapper()),
                CachedImageLoadingInfoRepository()
            )
        )
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}