package ru.skokova.android_academy.data.persistent

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MoviesDao {

    @Query("SELECT * FROM " + MoviesDatabaseContract.Movies.TABLE_NAME)
    suspend fun getAllMovies(): List<MovieEntity>

    @Query("SELECT * FROM " + MoviesDatabaseContract.Movies.TABLE_NAME + " WHERE " + MoviesDatabaseContract.Movies.COLUMN_NAME_ID + " == :movieId")
    suspend fun getMovie(movieId: Int): MovieEntity

    @Insert
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Update
    suspend fun updateMovie(movie: MovieEntity)

    @Query("DELETE FROM " + MoviesDatabaseContract.Movies.TABLE_NAME)
    suspend fun deleteMovies()
}