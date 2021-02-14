package ru.skokova.android_academy.data.persistent

import androidx.room.*

@Dao
interface ActorsDao {

    @Transaction
    @Query("SELECT * FROM movies WHERE movie_id == :movieId")
    suspend fun getActorsForMovie(movieId: Int): ActorsForMovie

    @Transaction
    suspend fun insertMovieActors(movieId: Int, actors: List<ActorEntity>) {
        insertActors(actors)
        actors.forEach { actorEntity ->
            insertMovieActor(MovieActorEntity(movieId, actorEntity.id))
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActors(actors: List<ActorEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieActor(movieActorEntity: MovieActorEntity)

    @Query("DELETE FROM actors")
    suspend fun deleteActors()

    @Query("DELETE FROM movie_actor")
    suspend fun deleteMovieActors()

    @Transaction
    suspend fun deleteActorsForMovies() {
        deleteActors()
        deleteMovieActors()
    }
}