package ru.skokova.android_academy.data.mapper

import ru.skokova.android_academy.data.model.Actor
import ru.skokova.android_academy.data.persistent.ActorEntity

class EntityActorsMapper {
    fun toActor(actorEntity: ActorEntity) =
        Actor(actorEntity.id, actorEntity.name, actorEntity.picture)

    fun toEntity(actor: Actor) = ActorEntity(actor.id, actor.name, actor.picture)
}