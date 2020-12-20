package ru.skokova.android_academy.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

private suspend fun loadGenres(context: Context): List<Genre> = withContext(Dispatchers.IO) {
    val data = readAssetFileToString(context, "genres.json")
    parseGenres(data)
}

internal suspend fun parseGenres(data: String): List<Genre> = withContext(Dispatchers.Default) {
    val jsonGenres = jsonFormat.decodeFromString<List<JsonGenre>>(data)
    jsonGenres.map { Genre(id = it.id, name = it.name) }
}

private fun readAssetFileToString(context: Context, fileName: String): String {
    val stream = context.assets.open(fileName)
    return stream.bufferedReader().readText()
}

private suspend fun loadActors(context: Context): List<Actor> = withContext(Dispatchers.IO) {
    val data = readAssetFileToString(context, "people.json")
    parseActors(data)
}

internal suspend fun parseActors(data: String): List<Actor> = withContext(Dispatchers.Default) {
    val jsonActors = jsonFormat.decodeFromString<List<JsonActor>>(data)
    jsonActors.map { Actor(id = it.id, name = it.name, picture = it.profilePicture) }
}

@Suppress("unused")
internal suspend fun loadMovies(context: Context): List<Movie> = withContext(Dispatchers.IO) {
    val genresMap = loadGenres(context)
    val actorsMap = loadActors(context)

    val data = readAssetFileToString(context, "data.json")
    parseMovies(data, genresMap, actorsMap)
}

internal suspend fun parseMovies(
    data: String,
    genres: List<Genre>,
    actors: List<Actor>
): List<Movie> = withContext(Dispatchers.Default) {
    val genresMap = genres.associateBy { it.id }
    val actorsMap = actors.associateBy { it.id }

    val jsonMovies = jsonFormat.decodeFromString<List<JsonMovie>>(data)

    jsonMovies.map { jsonMovie ->
        @Suppress("unused")
        toMovie(jsonMovie, genresMap, actorsMap)
    }
}

internal suspend fun loadMovie(id: Int, context: Context): Movie? = withContext(Dispatchers.IO) {
    val genresMap = loadGenres(context)
    val actorsMap = loadActors(context)

    val data = readAssetFileToString(context, "data.json")
    parseMovie(id, data, genresMap, actorsMap)
}

internal suspend fun parseMovie(
    id: Int,
    data: String,
    genres: List<Genre>,
    actors: List<Actor>
): Movie? = withContext(Dispatchers.Default) {
    val genresMap = genres.associateBy { it.id }
    val actorsMap = actors.associateBy { it.id }

    val jsonMovies = jsonFormat.decodeFromString<List<JsonMovie>>(data)

    val jsonMovie = jsonMovies.find { jsonMovie -> jsonMovie.id == id }
    jsonMovie?.let { _ ->
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