package ru.skokova.android_academy.movie

import androidx.annotation.DrawableRes

class Movie(
    val name: String,
    @DrawableRes val listPoster: Int,
    @DrawableRes val detailsPoster: Int,
    val rating: Float,
    val reviews: Int,
    val tags: String,
    @DrawableRes val like: Int,
    val pg: String,
    val duration: Int,
    val description: String
)