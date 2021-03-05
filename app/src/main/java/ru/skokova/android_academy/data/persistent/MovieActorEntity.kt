package ru.skokova.android_academy.data.persistent

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = MoviesDatabaseContract.MovieActor.TABLE_NAME,
    primaryKeys = [MoviesDatabaseContract.MovieActor.COLUMN_NAME_MOVIE_ID, MoviesDatabaseContract.MovieActor.COLUMN_NAME_ACTOR_ID]
)
class MovieActorEntity(
    @ColumnInfo(name = MoviesDatabaseContract.MovieActor.COLUMN_NAME_MOVIE_ID)
    val movieId: Int,
    @ColumnInfo(name = MoviesDatabaseContract.MovieActor.COLUMN_NAME_ACTOR_ID)
    val actorId: Int
)