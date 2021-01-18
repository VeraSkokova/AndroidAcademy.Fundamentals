package ru.skokova.android_academy.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GenresResponse(@SerialName("genres") val genres: List<GenresResponseItem>)

@Serializable
class GenresResponseItem(@SerialName("id") val id: Int, @SerialName("name") val name: String)