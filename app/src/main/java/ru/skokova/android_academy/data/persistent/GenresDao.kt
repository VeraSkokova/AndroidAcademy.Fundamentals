package ru.skokova.android_academy.data.persistent

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GenresDao {

    @Query("SELECT * FROM " + MoviesDatabaseContract.Genres.TABLE_NAME)
    suspend fun getAllGenres(): List<GenreEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<GenreEntity>)

    @Query("DELETE FROM " + MoviesDatabaseContract.Genres.TABLE_NAME)
    suspend fun deleteGenres()
}