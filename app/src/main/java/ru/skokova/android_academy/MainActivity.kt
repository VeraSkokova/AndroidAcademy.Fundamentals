package ru.skokova.android_academy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

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

    override fun onClick(id: Int) {
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.container, FragmentMoviesDetails.newInstance(id))
                addToBackStack(null)
                commit()
            }
    }
}