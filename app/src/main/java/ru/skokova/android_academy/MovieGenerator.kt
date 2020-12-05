package ru.skokova.android_academy

import android.content.Context

class MovieGenerator(context: Context?) {
    private val emptyReviews = "0 reviews"
    private val emptyDuration = "-"

    private val movies = hashMapOf(
        0 to Movie(
            "Avengers: End Game",
            R.drawable.avengers,
            R.drawable.orig,
            4.0f,
            context?.resources?.getQuantityString(R.plurals.movie_reviews, 125, 125) ?: emptyReviews,
            "Action, Adventure, Fantasy",
            R.drawable.ic_like,
            "13+",
            context?.getString(R.string.movie_duration, "137") ?: emptyDuration,
            "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos\\' actions and restore balance to the universe."
        ),
        1 to Movie(
            "Tenet",
            R.drawable.tenet,
            R.drawable.tenet,
            5.0f,
            context?.resources?.getQuantityString(R.plurals.movie_reviews, 98, 98) ?: emptyReviews,
            "Action, Sci-Fi, Thriller",
            R.drawable.ic_liked,
            "16+",
            context?.getString(R.string.movie_duration, "97") ?: emptyDuration,
            "Armed with only one word, Tenet, and fighting for the survival of the entire world, a Protagonist journeys through a twilight world of international espionage on a mission that will unfold in something beyond real time."
        ),
        2 to Movie(
            "Black Widow",
            R.drawable.black_widow,
            R.drawable.black_widow,
            4.0f,
            context?.resources?.getQuantityString(R.plurals.movie_reviews, 38, 38) ?: emptyReviews,
            "Action, Adventure, Sci-Fi",
            R.drawable.ic_like,
            "13+",
            context?.getString(R.string.movie_duration, "102") ?: emptyDuration,
            "A film about Natasha Romanoff in her quests between the films Civil War and Infinity War."
        ),
        3 to Movie(
            "Wonder Woman 1984",
            R.drawable.wonder_woman,
            R.drawable.wonder_woman,
            5.0f,
            context?.resources?.getQuantityString(R.plurals.movie_reviews, 74, 74) ?: emptyReviews,
            "Action, Adventure, Fantasy",
            R.drawable.ic_like,
            "13+",
            context?.getString(R.string.movie_duration, "120") ?: emptyDuration,
            "Fast forward to the 1980s as Wonder Woman's next big screen adventure finds her facing two all-new foes: Max Lord and The Cheetah."
        )
    )

    fun getMovies(): List<Movie> = movies.values.toList()

    fun getMovieById(id: Int) = movies[id]
}