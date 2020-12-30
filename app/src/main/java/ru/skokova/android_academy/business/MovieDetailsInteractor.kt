package ru.skokova.android_academy.business

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.skokova.android_academy.data.Movie
import ru.skokova.android_academy.data.MovieDetailsRepository

class MovieDetailsInteractor(private val movieDetailsRepository: MovieDetailsRepository) {
    suspend fun getMovieById(id: Int): Movie? = withContext(Dispatchers.IO) {
        movieDetailsRepository.loadMovie(id)
    }
}