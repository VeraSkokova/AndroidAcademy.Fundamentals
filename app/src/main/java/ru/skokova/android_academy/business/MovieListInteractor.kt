package ru.skokova.android_academy.business

import ru.skokova.android_academy.data.Movie
import ru.skokova.android_academy.data.MovieListRepository

class MovieListInteractor(private val movieListRepository: MovieListRepository) {
    suspend fun getMovies(): List<Movie> = movieListRepository.loadMovies()
}