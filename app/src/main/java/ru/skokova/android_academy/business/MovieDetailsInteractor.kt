package ru.skokova.android_academy.business

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.skokova.android_academy.data.MovieDetailsRepository
import ru.skokova.android_academy.data.converter.MovieMapper
import ru.skokova.android_academy.data.model.Movie

class MovieDetailsInteractor(private val movieDetailsRepository: MovieDetailsRepository) {
    suspend fun getMovieById(id: Int): Movie = withContext(Dispatchers.IO) {
        val loadMovie = movieDetailsRepository.loadMovie(id)
        val movieMapper = MovieMapper()
        movieMapper.toMovie(loadMovie)
    }
}