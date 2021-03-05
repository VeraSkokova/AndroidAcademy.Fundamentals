package ru.skokova.android_academy.data.persistent

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = MoviesDatabaseContract.Actors.TABLE_NAME,
    indices = [Index(MoviesDatabaseContract.Actors.COLUMN_NAME_ID)]
)
class ActorEntity(
    @PrimaryKey
    @ColumnInfo(name = MoviesDatabaseContract.Actors.COLUMN_NAME_ID)
    val id: Int,
    @ColumnInfo(name = MoviesDatabaseContract.Actors.COLUMN_NAME_ACTOR_NAME)
    val name: String,
    @ColumnInfo(name = MoviesDatabaseContract.Actors.COLUMN_NAME_PICTURE)
    val picture: String
)