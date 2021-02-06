package ru.skokova.android_academy.data.persistent

import android.provider.BaseColumns

object MoviesDatabaseContract {

    const val DATABASE_NAME = "TmdbMovies.db"

    object Movies {
        const val TABLE_NAME = "movies"
        const val COLUMN_NAME_ID = "movie_id"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_OVERVIEW = "overview"
        const val COLUMN_NAME_POSTER = "poster_url"
        const val COLUMN_NAME_BACKDROP = "backdrop_url"
        const val COLUMN_NAME_RATINGS = "rating"
        const val COLUMN_NUMBER_OF_RATINGS = "number_of_ratings"
        const val COLUMN_NAME_MINIMUM_AGE = "min_age"
        const val COLUMN_NAME_RUNTIME = "runtime"
        const val COLUMN_NAME_GENRES = "genres"
    }

    object Genres {
        const val TABLE_NAME = "genres"
        const val COLUMN_NAME_ID = BaseColumns._ID
        const val COLUMN_NAME_GENRE_NAME = "name"
    }

    object Actors {
        const val TABLE_NAME = "actors"
        const val COLUMN_NAME_ID = "actor_id"
        const val COLUMN_NAME_ACTOR_NAME = "name"
        const val COLUMN_NAME_PICTURE = "picture_url"
    }

    object MovieActor {
        const val TABLE_NAME = "movie_actor"
        const val COLUMN_NAME_MOVIE_ID = "movie_id"
        const val COLUMN_NAME_ACTOR_ID = "actor_id"
    }
}