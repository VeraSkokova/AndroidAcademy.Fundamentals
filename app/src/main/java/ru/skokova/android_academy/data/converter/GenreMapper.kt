package ru.skokova.android_academy.data.converter

import ru.skokova.android_academy.data.model.Genre
import ru.skokova.android_academy.data.network.GenresResponseItem

class GenreMapper {
    fun toGenre(genresResponseItem: GenresResponseItem): Genre {
        return Genre(genresResponseItem.id, genresResponseItem.name)
    }
}