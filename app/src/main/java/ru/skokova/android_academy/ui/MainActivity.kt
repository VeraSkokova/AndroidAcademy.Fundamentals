package ru.skokova.android_academy.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.skokova.android_academy.R

class MainActivity : AppCompatActivity(), FragmentMoviesList.MovieClickListener,
    FragmentMoviesDetails.MovieDetailsNavigationListener, FragmentMoviesList.ConfigurationListener,
    FragmentMoviesDetails.ConfigurationListener {

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

    override fun getPosterImageUrl(): String? {
        return getPreferences(Context.MODE_PRIVATE).getString(POSTER_IMAGE_URL, null)
    }

    override fun getBackdropImageUrl(): String? {
        return getPreferences(Context.MODE_PRIVATE).getString(BACKDROP_IMAGE_URL, null)
    }

    override fun getProfileImageUrl(): String? {
        return getPreferences(Context.MODE_PRIVATE).getString(PROFILE_IMAGE_URL, null)
    }

    override fun setBaseImageUrls(backdropUrl: String, posterUrl: String, profileUrl: String) {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(BACKDROP_IMAGE_URL, backdropUrl)
            putString(POSTER_IMAGE_URL, posterUrl)
            putString(PROFILE_IMAGE_URL, profileUrl)
            apply()
        }

    }

    companion object {
        const val BACKDROP_IMAGE_URL = "backdrop_image_url"
        const val POSTER_IMAGE_URL = "poster_image_url"
        const val PROFILE_IMAGE_URL = "profile_image_url"
    }
}