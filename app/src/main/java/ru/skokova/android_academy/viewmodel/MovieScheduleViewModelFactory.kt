package ru.skokova.android_academy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.skokova.android_academy.business.MovieWatchUseCase
import ru.skokova.android_academy.data.model.Movie
import ru.skokova.android_academy.system.CalendarManager

class MovieScheduleViewModelFactory(private val movie: Movie) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MovieScheduleViewModel::class.java -> MovieScheduleViewModel(
            movie, MovieWatchUseCase(
                CalendarManager()
            )
        )
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}