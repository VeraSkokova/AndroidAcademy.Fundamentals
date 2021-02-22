package ru.skokova.android_academy.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import ru.skokova.android_academy.R

class MainActivity : AppCompatActivity(), FragmentMoviesList.MovieClickListener,
    FragmentMoviesDetails.MovieDetailsNavigationListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, FragmentMoviesList())
                .commit()
            intent?.let(::handleIntent)
        }
    }

    override fun onClick(id: Int) {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, FragmentMoviesDetails.newInstance(id))
            .addToBackStack(FragmentMoviesDetails.TAG)
            .commit()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        if (intent?.action == Intent.ACTION_VIEW) {
            val pathPart = intent.data?.lastPathSegment
            val movieId = pathPart?.split('?')?.getOrNull(0)?.toIntOrNull()
            movieId?.let { openMovie(movieId) }
        }
    }

    private fun openMovie(movieId: Int) {
        supportFragmentManager.popBackStack(FragmentMoviesDetails.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.beginTransaction()
            .add(R.id.container, FragmentMoviesDetails.newInstance(movieId))
            .addToBackStack(FragmentMoviesDetails.TAG)
            .commit()
    }
}