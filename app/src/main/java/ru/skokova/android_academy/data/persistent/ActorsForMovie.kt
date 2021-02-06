package ru.skokova.android_academy.data.persistent

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ActorsForMovie(
    @Embedded val movie: MovieEntity,
    @Relation(
        parentColumn = MoviesDatabaseContract.MovieActor.COLUMN_NAME_MOVIE_ID,
        entityColumn = MoviesDatabaseContract.MovieActor.COLUMN_NAME_ACTOR_ID,
        associateBy = Junction(MovieActorEntity::class)
    )
    val actors: List<ActorEntity>
)