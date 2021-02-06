package ru.skokova.android_academy.data.persistent

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = MoviesDatabaseContract.Genres.TABLE_NAME,
    indices = [Index(MoviesDatabaseContract.Genres.COLUMN_NAME_ID)]
)
class GenreEntity(
    @PrimaryKey
    @ColumnInfo(name = MoviesDatabaseContract.Genres.COLUMN_NAME_ID)
    val id: Int,
    @ColumnInfo(name = MoviesDatabaseContract.Genres.COLUMN_NAME_GENRE_NAME)
    val name: String
)