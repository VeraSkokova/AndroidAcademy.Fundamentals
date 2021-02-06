package ru.skokova.android_academy.business

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.skokova.android_academy.data.GenresCachingRepository
import ru.skokova.android_academy.data.GenresRepository
import ru.skokova.android_academy.data.MovieListCachingRepository
import ru.skokova.android_academy.data.MovieListRepository
import ru.skokova.android_academy.data.model.Movie

class MovieListInteractor(
    private val movieListRepository: MovieListRepository,
    private val movieListCacheRepository: MovieListCachingRepository,
    private val genresRepository: GenresRepository,
    private val genresCacheRepository: GenresCachingRepository
) {
    suspend fun getMovies(): List<Movie> = withContext(Dispatchers.IO) {
        if (genresCacheRepository.loadGenres().isEmpty()) {
            val genres = genresRepository.loadGenres()
            genresCacheRepository.saveGenres(genres)
        }

        val movies = movieListRepository.loadMovies()
        movieListCacheRepository.deleteOldMovies()
        movieListCacheRepository.saveMovies(movies)
        movies
    }

    suspend fun getCachedMovies(): List<Movie> = withContext(Dispatchers.IO) {
        movieListCacheRepository.loadMovies()
    }
}