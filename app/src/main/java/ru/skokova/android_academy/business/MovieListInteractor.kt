package ru.skokova.android_academy.business

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.skokova.android_academy.data.*
import ru.skokova.android_academy.data.model.Movie

class MovieListInteractor(
    private val movieListRepository: MovieListRepository,
    private val movieListCacheRepository: MovieListCachingRepository,
    private val genresRepository: GenresRepository,
    private val genresCacheRepository: GenresCachingRepository,
    private val notificationsHelper: NotificationsHelper
) {

    suspend fun getMovies(): List<Movie> = withContext(Dispatchers.IO) {
        if (genresCacheRepository.loadGenres().isEmpty()) {
            val genres = genresRepository.loadGenres()
            genresCacheRepository.saveGenres(genres)
        }

        try {
            val movies = movieListRepository.loadMovies()
            val topMovie = movies.maxByOrNull { movie -> movie.ratings }
            topMovie?.let { notificationsHelper.showNotification(it) }
            movieListCacheRepository.saveMovies(movies)
            movies
        } catch (e: Exception) {
            if (InteractorUtils.isNetworkException(e)) {
                movieListCacheRepository.loadMovies()
            } else {
                throw e
            }
        }
    }
}