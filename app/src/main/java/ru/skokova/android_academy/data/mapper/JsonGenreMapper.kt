package ru.skokova.android_academy.data.mapper

import ru.skokova.android_academy.data.model.Genre
import ru.skokova.android_academy.data.network.GenresResponseItem

class JsonGenreMapper {
    fun toGenre(genresResponseItem: GenresResponseItem): Genre {
        return Genre(genresResponseItem.id, genresResponseItem.name)
    }
}