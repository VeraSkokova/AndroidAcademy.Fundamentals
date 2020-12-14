package ru.skokova.android_academy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), FragmentMoviesList.MovieClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, FragmentMoviesList())
                .commit()
        }
    }

    override fun onClick() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, FragmentMoviesDetails())
            .addToBackStack(null)
            .commit()
    }
}