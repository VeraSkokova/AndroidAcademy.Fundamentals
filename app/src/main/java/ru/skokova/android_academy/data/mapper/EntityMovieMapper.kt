package ru.skokova.android_academy.data.mapper

import ru.skokova.android_academy.data.model.Movie
import ru.skokova.android_academy.data.persistent.MovieEntity

class EntityMovieMapper {
    fun toMovie(movieEntity: MovieEntity) = Movie(
        id = movieEntity.id,
        title = movieEntity.title,
        overview = movieEntity.overview,
        poster = movieEntity.poster,
        backdrop = movieEntity.backdrop,
        ratings = movieEntity.ratings,
        numberOfRatings = movieEntity.numberOfRatings,
        minimumAge = movieEntity.minimumAge,
        runtime = movieEntity.runtime,
        genres = movieEntity.genres
    )

    fun toEntity(movie: Movie): MovieEntity = MovieEntity(
        id = movie.id,
        title = movie.title,
        overview = movie.overview,
        poster = movie.poster,
        backdrop = movie.backdrop,
        ratings = movie.ratings,
        numberOfRatings = movie.numberOfRatings,
        minimumAge = movie.minimumAge,
        runtime = movie.runtime,
        genres = movie.genres
    )
}