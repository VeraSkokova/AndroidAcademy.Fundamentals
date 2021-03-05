package ru.skokova.android_academy.data.persistent

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.skokova.android_academy.MoviesApplication

@Database(
    entities = [MovieEntity::class, GenreEntity::class, ActorEntity::class, MovieActorEntity::class],
    version = 1
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract val moviesDao: MoviesDao
    abstract val genresDao: GenresDao
    abstract val actorsDao: ActorsDao

    companion object {
        val instance: MoviesDatabase by lazy {
            Room.databaseBuilder(
                MoviesApplication.context,
                MoviesDatabase::class.java,
                MoviesDatabaseContract.DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}