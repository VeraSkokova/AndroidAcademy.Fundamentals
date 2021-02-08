package ru.skokova.android_academy.data.converter

import ru.skokova.android_academy.data.model.Genre
import ru.skokova.android_academy.data.model.Movie
import ru.skokova.android_academy.data.network.MovieDetailsResponse
import ru.skokova.android_academy.data.network.MovieListItemResponse

class MovieMapper {
    fun toMovie(
        jsonMovie: MovieListItemResponse,
        genresMap: Map<Int, Genre>
    ) = Movie(id = jsonMovie.id,
        title = jsonMovie.title,
        overview = jsonMovie.overview,
        poster = jsonMovie.posterPicture,
        backdrop = jsonMovie.backdropPicture,
        ratings = jsonMovie.ratings / RATING_RATIO,
        numberOfRatings = jsonMovie.votesCount,
        minimumAge = if (jsonMovie.adult) ADULT_AGE else NOT_ADULT_AGE,
        runtime = 0,
        genres = jsonMovie.genreIds
            .joinToString {
                genresMap[it]?.name ?: throw IllegalArgumentException("Genre not found")
            }
    )

    fun toMovie(jsonMovie: MovieDetailsResponse) = Movie(
        id = jsonMovie.id,
        title = jsonMovie.title,
        overview = jsonMovie.overview,
        poster = jsonMovie.posterPicture,
        backdrop = jsonMovie.backdropPicture,
        ratings = jsonMovie.ratings / RATING_RATIO,
        numberOfRatings = jsonMovie.votesCount,
        minimumAge = if (jsonMovie.adult) ADULT_AGE else NOT_ADULT_AGE,
        runtime = jsonMovie.runtime,
        genres = jsonMovie.genres.joinToString { it.name }
    )

    companion object {
        const val RATING_RATIO = 2
        const val ADULT_AGE = 16
        const val NOT_ADULT_AGE = 13
    }
}