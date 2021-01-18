package ru.skokova.android_academy.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ActorsResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("cast")
    val cast: List<ActorsItemResponse>
)

@Serializable
class ActorsItemResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("profile_path")
    val profilePicture: String?
)