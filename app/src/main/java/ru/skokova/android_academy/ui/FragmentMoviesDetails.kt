package ru.skokova.android_academy.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.skokova.android_academy.R
import ru.skokova.android_academy.data.Resource
import ru.skokova.android_academy.data.model.Actor
import ru.skokova.android_academy.data.model.Movie
import ru.skokova.android_academy.data.model.MovieDetails
import ru.skokova.android_academy.ui.actor.ActorListDecoration
import ru.skokova.android_academy.ui.actor.AdapterActors
import ru.skokova.android_academy.viewmodel.ImageLoadingInfoViewModel
import ru.skokova.android_academy.viewmodel.MovieDetailsViewModel
import ru.skokova.android_academy.viewmodel.MovieDetailsViewModelFactory
import ru.skokova.android_academy.viewmodel.MovieViewModelFactory

class FragmentMoviesDetails : Fragment() {
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private val imageLoadingInfoViewModel: ImageLoadingInfoViewModel by viewModels { MovieViewModelFactory() }

    private val actorsAdapter = AdapterActors()

    private var navigationListener: MovieDetailsNavigationListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.tv_back)
            .setOnClickListener { navigationListener?.onBackPressed() }
        view.findViewById<View>(R.id.img_back)
            .setOnClickListener { navigationListener?.onBackPressed() }
        view.findViewById<RecyclerView>(R.id.rv_actors).apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = actorsAdapter
            addItemDecoration(
                ActorListDecoration(
                    context.resources.getDimension(R.dimen.actor_list_decor_margin).toInt()
                )
            )
        }

        val movieId = arguments?.getInt(MOVIE_ID)
        movieId?.let {
            movieDetailsViewModel =
                ViewModelProvider(this, MovieDetailsViewModelFactory(movieId)).get(
                    MovieDetailsViewModel::class.java
                )
        } ?: run {
            Toast.makeText(context, R.string.load_error, Toast.LENGTH_SHORT).show()
            navigationListener?.onBackPressed()
        }

        movieDetailsViewModel.movieDetailsLiveData.observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Success -> updateMovieInformation(view, resource.data)
                is Resource.Error -> {
                    Toast.makeText(context, resource.message, Toast.LENGTH_SHORT)
                        .show()
                    navigationListener?.onBackPressed()
                }
            }
        })
    }

    private fun loadPosters(movie: Movie, actors: List<Actor>, view: View) {
        imageLoadingInfoViewModel.configurationLiveData.observe(
            viewLifecycleOwner,
            { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val backdropUrl = resource.data.baseBackdropImageUrl
                        val profileUrl = resource.data.baseProfileImageUrl
                        updatePosters(movie, actors, view, backdropUrl, profileUrl)
                    }
                    is Resource.Error -> Toast.makeText(
                        context,
                        resource.message,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        )
    }


    private fun updateMovieInformation(
        view: View,
        movieDetails: MovieDetails
    ) {
        val movie = movieDetails.movie
        val actors = movieDetails.actors

        view.findViewById<TextView>(R.id.tv_movie_name).text = movie.title
        view.findViewById<TextView>(R.id.tv_pg).text =
            context?.resources?.getString(R.string.pg, movie.minimumAge)
        view.findViewById<TextView>(R.id.tv_tags).text = movie.genres
        view.findViewById<RatingBar>(R.id.rating).rating = movie.ratings
        view.findViewById<TextView>(R.id.tv_reviews).text =
            context?.resources?.getQuantityString(
                R.plurals.movie_reviews,
                movie.numberOfRatings,
                movie.numberOfRatings
            )
        view.findViewById<TextView>(R.id.tv_description).text = movie.overview
        loadPosters(movie, actors, view)
    }

    private fun updatePosters(
        movie: Movie,
        actors: List<Actor>,
        view: View,
        backdropUrl: String?,
        profileUrl: String?
    ) {
        backdropUrl?.let {
            Glide.with(this)
                .load(backdropUrl + movie.backdrop)
                .apply(imageOption)
                .into(view.findViewById(R.id.iv_poster))
        }
        profileUrl?.let {
            actorsAdapter.updateActors(actors, profileUrl)
        } ?: run {
            Toast.makeText(context, R.string.actors_error, Toast.LENGTH_SHORT).show()
            view.findViewById<View>(R.id.tv_cast_title).visibility = View.GONE
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigationListener = activity as MovieDetailsNavigationListener
    }

    override fun onDetach() {
        navigationListener = null
        super.onDetach()
    }

    interface MovieDetailsNavigationListener {
        fun onBackPressed()
    }

    companion object {
        private const val MOVIE_ID = "movie_id"

        private val imageOption = RequestOptions()
            .placeholder(R.drawable.ic_baseline_movie)
            .fallback(R.drawable.ic_baseline_movie)
            .centerCrop()

        fun newInstance(movieId: Int): FragmentMoviesDetails {
            val fragment = FragmentMoviesDetails()
            val args = Bundle()
            args.putInt(MOVIE_ID, movieId)
            fragment.arguments = args
            return fragment
        }
    }
}