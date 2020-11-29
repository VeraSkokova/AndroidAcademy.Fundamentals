package ru.skokova.android_academy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), FragmentMoviesList.MovieClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .apply {
                    add(R.id.container, FragmentMoviesList())
                    commit()
                }
        }
    }

    override fun onClick() {
        supportFragmentManager.beginTransaction()
            .apply {
                add(R.id.container, FragmentMoviesDetails())
                addToBackStack(null)
                commit()
            }
    }
}