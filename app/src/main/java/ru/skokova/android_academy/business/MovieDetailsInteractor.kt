package ru.skokova.android_academy.business

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.skokova.android_academy.data.ActorsCachingRepository
import ru.skokova.android_academy.data.ActorsRepository
import ru.skokova.android_academy.data.MovieDetailsCachingRepository
import ru.skokova.android_academy.data.MovieDetailsRepository
import ru.skokova.android_academy.data.model.Actor
import ru.skokova.android_academy.data.model.Movie
import ru.skokova.android_academy.data.model.MovieDetails

class MovieDetailsInteractor(
    private val movieDetailsRepository: MovieDetailsRepository,
    private val movieDetailsCacheRepository: MovieDetailsCachingRepository,
    private val actorsRepository: ActorsRepository,
    private val actorsCacheRepository: ActorsCachingRepository
) {
    suspend fun getMovieById(id: Int): MovieDetails = withContext(Dispatchers.IO) {
        val movie = getMovie(id)
        val actors = getActors(id)
        MovieDetails(movie, actors)
    }

    private suspend fun getMovie(id: Int): Movie {
        return try {
            val movie = movieDetailsRepository.loadMovie(id)
            movieDetailsCacheRepository.saveMovie(movie)
            movie
        } catch (e: Exception) {
            if (InteractorUtils.isNetworkException(e)) {
                movieDetailsCacheRepository.loadMovie(id)
            } else {
                throw e
            }
        }
    }

    private suspend fun getActors(movieId: Int): List<Actor> {
        return try {
            val actors = actorsRepository.getActors(movieId)
            actorsCacheRepository.saveActors(movieId, actors)
            actors
        } catch (e: Exception) {
            if (InteractorUtils.isNetworkException(e)) {
                actorsCacheRepository.getActors(movieId)
            } else {
                throw e
            }
        }
    }
}