package ru.skokova.android_academy.data.converter

import ru.skokova.android_academy.data.model.Actor
import ru.skokova.android_academy.data.network.ActorsResponse

class ActorsMapper {
    fun toActors(actorsResponse: ActorsResponse): List<Actor> {
        return actorsResponse.cast.mapNotNull { actor ->
            actor.profilePicture?.let {
                Actor(actor.id, actor.name, actor.profilePicture)
            }
        }
    }
}