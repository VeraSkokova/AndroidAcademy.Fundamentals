package ru.skokova.android_academy.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
        }
    }

    override fun onClick(id: Int) {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, FragmentMoviesDetails.newInstance(id))
            .addToBackStack(null)
            .commit()
    }
}