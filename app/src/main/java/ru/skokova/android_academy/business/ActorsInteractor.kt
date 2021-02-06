package ru.skokova.android_academy.business

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.skokova.android_academy.data.ActorsCachingRepository
import ru.skokova.android_academy.data.ActorsRepository
import ru.skokova.android_academy.data.model.Actor

class ActorsInteractor(
    private val actorsRepository: ActorsRepository,
    private val actorsCacheRepository: ActorsCachingRepository
) {
    suspend fun getMovieActors(movieId: Int): List<Actor> = withContext(Dispatchers.IO) {
        val actors = actorsRepository.getActors(movieId)
        actorsCacheRepository
        actorsCacheRepository.saveActors(movieId, actors)
        actors
    }

    suspend fun getCachedMovieActors(movieId: Int): List<Actor> = withContext(Dispatchers.IO) {
        actorsCacheRepository.getActors(movieId)
    }
}