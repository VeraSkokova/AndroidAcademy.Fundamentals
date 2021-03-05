package ru.skokova.android_academy.data.persistent

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = MoviesDatabaseContract.Movies.TABLE_NAME,
    indices = [Index(MoviesDatabaseContract.Movies.COLUMN_NAME_ID)]
)
class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = MoviesDatabaseContract.Movies.COLUMN_NAME_ID)
    val id: Int,
    @ColumnInfo(name = MoviesDatabaseContract.Movies.COLUMN_NAME_TITLE)
    val title: String,
    @ColumnInfo(name = MoviesDatabaseContract.Movies.COLUMN_NAME_OVERVIEW)
    val overview: String,
    @ColumnInfo(name = MoviesDatabaseContract.Movies.COLUMN_NAME_POSTER)
    val poster: String,
    @ColumnInfo(name = MoviesDatabaseContract.Movies.COLUMN_NAME_BACKDROP)
    val backdrop: String,
    @ColumnInfo(name = MoviesDatabaseContract.Movies.COLUMN_NAME_RATINGS)
    val ratings: Float,
    @ColumnInfo(name = MoviesDatabaseContract.Movies.COLUMN_NUMBER_OF_RATINGS)
    val numberOfRatings: Int,
    @ColumnInfo(name = MoviesDatabaseContract.Movies.COLUMN_NAME_MINIMUM_AGE)
    val minimumAge: Int,
    @ColumnInfo(name = MoviesDatabaseContract.Movies.COLUMN_NAME_RUNTIME)
    val runtime: Int,
    @ColumnInfo(name = MoviesDatabaseContract.Movies.COLUMN_NAME_GENRES)
    val genres: String
)