package ru.skokova.android_academy.business

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.skokova.android_academy.data.MovieDetailsCachingRepository
import ru.skokova.android_academy.data.MovieDetailsRepository
import ru.skokova.android_academy.data.model.Movie

class MovieDetailsInteractor(
    private val movieDetailsRepository: MovieDetailsRepository,
    private val movieDetailsCacheRepository: MovieDetailsCachingRepository
) {
    suspend fun getMovieById(id: Int): Movie = withContext(Dispatchers.IO) {
        val movie = movieDetailsRepository.loadMovie(id)
        movieDetailsCacheRepository.saveMovie(movie)
        movie
    }

    suspend fun getCachedMovie(id: Int): Movie = withContext(Dispatchers.IO) {
        movieDetailsCacheRepository.loadMovie(id)
    }
}