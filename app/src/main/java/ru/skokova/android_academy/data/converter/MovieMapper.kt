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
        minimumAge = if (jsonMovie.adult) 16 else 13,
        runtime = 0,
        genres = jsonMovie.genreIds
            .map {
                genresMap[it] ?: throw IllegalArgumentException("Genre not found")
            }
            .joinToString { it.name }
    )

    fun toMovie(jsonMovie: MovieDetailsResponse) = Movie(
        id = jsonMovie.id,
        title = jsonMovie.title,
        overview = jsonMovie.overview,
        poster = jsonMovie.posterPicture,
        backdrop = jsonMovie.backdropPicture,
        ratings = jsonMovie.ratings / RATING_RATIO,
        numberOfRatings = jsonMovie.votesCount,
        minimumAge = if (jsonMovie.adult) 16 else 13,
        runtime = jsonMovie.runtime,
        genres = jsonMovie.genres.joinToString { it.name }
    )

    companion object {
        const val RATING_RATIO = 2
    }
}