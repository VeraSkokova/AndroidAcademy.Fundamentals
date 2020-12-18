package ru.skokova.android_academy.movie

import ru.skokova.android_academy.R

object MovieGenerator {
    private val movies = hashMapOf(
        0 to Movie(
            "Avengers: End Game",
            R.drawable.avengers,
            R.drawable.orig,
            4.0f,
            125,
            "Action, Adventure, Fantasy",
            R.drawable.ic_like,
            "13+",
            137,
            "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos\\' actions and restore balance to the universe."
        ),
        1 to Movie(
            "Tenet",
            R.drawable.tenet,
            R.drawable.tenet_wide,
            5.0f,
            98,
            "Action, Sci-Fi, Thriller",
            R.drawable.ic_liked,
            "16+",
            97,
            "Armed with only one word, Tenet, and fighting for the survival of the entire world, a Protagonist journeys through a twilight world of international espionage on a mission that will unfold in something beyond real time."
        ),
        2 to Movie(
            "Black Widow",
            R.drawable.black_widow,
            R.drawable.black_widow_wide,
            4.0f,
            38,
            "Action, Adventure, Sci-Fi",
            R.drawable.ic_like,
            "13+",
            102,
            "A film about Natasha Romanoff in her quests between the films Civil War and Infinity War."
        ),
        3 to Movie(
            "Wonder Woman 1984",
            R.drawable.wonder_woman,
            R.drawable.wonder_woman_wide,
            5.0f,
            74,
            "Action, Adventure, Fantasy",
            R.drawable.ic_like,
            "13+",
            120,
            "Fast forward to the 1980s as Wonder Woman's next big screen adventure finds her facing two all-new foes: Max Lord and The Cheetah."
        )
    )

    fun getMovies(): List<Movie> = movies.values.toList()

    fun getMovieById(id: Int) = movies[id]
}