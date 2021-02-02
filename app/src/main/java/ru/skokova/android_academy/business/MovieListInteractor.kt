package ru.skokova.android_academy.business

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.skokova.android_academy.data.Movie
import ru.skokova.android_academy.data.MovieListRepository

class MovieListInteractor(private val movieListRepository: MovieListRepository) {
    suspend fun getMovies(): List<Movie> = withContext(Dispatchers.IO) {
        movieListRepository.loadMovies()
    }
}