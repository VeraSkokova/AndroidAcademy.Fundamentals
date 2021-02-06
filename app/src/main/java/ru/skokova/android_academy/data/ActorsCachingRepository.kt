package ru.skokova.android_academy.data

import ru.skokova.android_academy.data.mapper.EntityActorsMapper
import ru.skokova.android_academy.data.model.Actor
import ru.skokova.android_academy.data.persistent.MoviesDatabase

interface ActorsCachingRepository : ActorsRepository {
    suspend fun saveActors(movieId: Int, actors: List<Actor>)

    suspend fun deleteOldActors()
}

class DbActorsRepository(private val mapper: EntityActorsMapper) : ActorsCachingRepository {
    private val moviesDatabase = MoviesDatabase.instance

    override suspend fun getActors(movieId: Int): List<Actor> {
        val savedActors = moviesDatabase.actorsDao.getActorsForMovie(movieId)
        return savedActors.actors.map { actorEntity ->
            mapper.toActor(actorEntity)
        }
    }

    override suspend fun saveActors(movieId: Int, actors: List<Actor>) {
        val actorEntities = actors.map { mapper.toEntity(it) }
        moviesDatabase.actorsDao.insertMovieActors(movieId, actorEntities)
    }

    override suspend fun deleteOldActors() {
        moviesDatabase.actorsDao.deleteActorsForMovies()
    }
}