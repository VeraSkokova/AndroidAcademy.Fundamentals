package ru.skokova.android_academy.business

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.skokova.android_academy.data.ActorsRepository
import ru.skokova.android_academy.data.model.Actor

class ActorsInteractor(private val actorsRepository: ActorsRepository) {
    suspend fun getMovieActors(movieId: Int): List<Actor> = withContext(Dispatchers.IO) {
        actorsRepository.getActors(movieId)
    }
}