package ru.skokova.android_academy.data.mapper

import ru.skokova.android_academy.data.model.Genre
import ru.skokova.android_academy.data.persistent.GenreEntity

class EntityGenreMapper {
    fun toGenre(genreEntity: GenreEntity): Genre {
        return Genre(genreEntity.id, genreEntity.name)
    }

    fun toEntity(genre: Genre): GenreEntity {
        return GenreEntity(genre.id, genre.name)
    }
}