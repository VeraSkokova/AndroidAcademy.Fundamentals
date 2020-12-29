package ru.skokova.android_academy.business

import ru.skokova.android_academy.data.Movie
import ru.skokova.android_academy.data.MovieDetailsRepository

class MovieDetailsInteractor(private val movieDetailsRepository: MovieDetailsRepository) {
    suspend fun getMovieById(id: Int): Movie? = movieDetailsRepository.loadMovie(id)
}