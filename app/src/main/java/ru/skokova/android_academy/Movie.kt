package ru.skokova.android_academy

import androidx.annotation.DrawableRes

class Movie(
    val name: String,
    @DrawableRes val listPoster: Int,
    @DrawableRes val detailsPoster: Int,
    val rating: Float,
    val reviews: String,
    val tags: String,
    @DrawableRes val like: Int,
    val pg: String,
    val duration: String,
    val description: String
)