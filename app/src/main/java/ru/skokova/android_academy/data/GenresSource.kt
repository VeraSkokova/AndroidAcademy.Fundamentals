package ru.skokova.android_academy.data

import ru.skokova.android_academy.data.model.Genre

object GenresSource {
    private val mutableGenres: MutableMap<Int, Genre> = mutableMapOf()
    val genres: Map<Int, Genre> get() = mutableGenres

    fun saveGenres(genresList: List<Genre>) {
        genresList.forEach { genre ->
            mutableGenres[genre.id] = genre
        }
    }
}