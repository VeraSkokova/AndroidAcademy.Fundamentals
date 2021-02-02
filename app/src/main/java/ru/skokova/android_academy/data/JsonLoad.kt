package ru.skokova.android_academy.data

import android.content.Context
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


private val jsonFormat = Json { ignoreUnknownKeys = true }

@Serializable
private class JsonGenre(val id: Int, val name: String)

@Serializable
private class JsonActor(
    val id: Int,
    val name: String,
    @SerialName("profile_path")
    val profilePicture: String
)

@Serializable
private class JsonMovie(
    val id: Int,
    val title: String,
    @SerialName("poster_path")
    val posterPicture: String,
    @SerialName("backdrop_path")
    val backdropPicture: String,
    val runtime: Int,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    val actors: List<Int>,
    @SerialName("vote_average")
    val ratings: Float,
    @SerialName("vote_count")
    val votesCount: Int,
    val overview: String,
    val adult: Boolean
)

private fun loadGenres(context: Context): List<Genre> {
    val data = readAssetFileToString(context, "genres.json")
    return parseGenres(data)
}

internal fun parseGenres(data: String): List<Genre> {
    val jsonGenres = jsonFormat.decodeFromString<List<JsonGenre>>(data)
    return jsonGenres.map { Genre(id = it.id, name = it.name) }
}

private fun readAssetFileToString(context: Context, fileName: String): String {
    val stream = context.assets.open(fileName)
    return stream.bufferedReader().readText()
}

private fun loadActors(context: Context): List<Actor> {
    val data = readAssetFileToString(context, "people.json")
    return parseActors(data)
}

internal fun parseActors(data: String): List<Actor> {
    val jsonActors = jsonFormat.decodeFromString<List<JsonActor>>(data)
    return jsonActors.map { Actor(id = it.id, name = it.name, picture = it.profilePicture) }
}

@Suppress("unused")
internal fun loadMovies(context: Context): List<Movie> {
    val genresMap = loadGenres(context)
    val actorsMap = loadActors(context)

    val data = readAssetFileToString(context, "data.json")
    return parseMovies(data, genresMap, actorsMap)
}

internal fun parseMovies(
    data: String,
    genres: List<Genre>,
    actors: List<Actor>
): List<Movie> {
    val genresMap = genres.associateBy { it.id }
    val actorsMap = actors.associateBy { it.id }

    val jsonMovies = jsonFormat.decodeFromString<List<JsonMovie>>(data)

    return jsonMovies.map { jsonMovie ->
        @Suppress("unused")
        toMovie(jsonMovie, genresMap, actorsMap)
    }
}

internal fun loadMovie(id: Int, context: Context): Movie? {
    val genresMap = loadGenres(context)
    val actorsMap = loadActors(context)

    val data = readAssetFileToString(context, "data.json")
    return parseMovie(id, data, genresMap, actorsMap)
}

internal fun parseMovie(
    id: Int,
    data: String,
    genres: List<Genre>,
    actors: List<Actor>
): Movie? {
    val genresMap = genres.associateBy { it.id }
    val actorsMap = actors.associateBy { it.id }

    val jsonMovies = jsonFormat.decodeFromString<List<JsonMovie>>(data)

    val jsonMovie = jsonMovies.find { jsonMovie -> jsonMovie.id == id }
    return jsonMovie?.let { _ ->
        toMovie(jsonMovie, genresMap, actorsMap)
    }
}

private fun toMovie(
    jsonMovie: JsonMovie,
    genresMap: Map<Int, Genre>,
    actorsMap: Map<Int, Actor>
) = Movie(id = jsonMovie.id,
    title = jsonMovie.title,
    overview = jsonMovie.overview,
    poster = jsonMovie.posterPicture,
    backdrop = jsonMovie.backdropPicture,
    ratings = jsonMovie.ratings / RATING_RATIO,
    numberOfRatings = jsonMovie.votesCount,
    minimumAge = if (jsonMovie.adult) 16 else 13,
    runtime = jsonMovie.runtime,
    genres = jsonMovie.genreIds
        .map {
            genresMap[it] ?: throw IllegalArgumentException("Genre not found")
        }
        .joinToString { it.name },
    actors = jsonMovie.actors.map {
        actorsMap[it] ?: throw IllegalArgumentException("Actor not found")
    })

const val RATING_RATIO = 2