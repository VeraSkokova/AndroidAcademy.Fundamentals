package ru.skokova.android_academy

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.*
import ru.skokova.android_academy.actor.ActorListDecoration
import ru.skokova.android_academy.actor.AdapterActors
import ru.skokova.android_academy.data.Movie
import ru.skokova.android_academy.data.loadMovie

class FragmentMoviesDetails : Fragment() {
    private val actorsAdapter = AdapterActors()
    private var navigationListener: MovieDetailsNavigationListener? = null

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        Toast.makeText(context, R.string.load_error, Toast.LENGTH_SHORT).show()
        Log.w(ERROR_TAG, "CoroutineExceptionHandler got $exception in $coroutineContext")
    }
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main + exceptionHandler)

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
            scope.launch {
                loadMovie(movieId, context!!)?.let { movie ->
                    updateMovieInformation(view, movie)
                }
            }
        }
    }

    private fun updateMovieInformation(
        view: View,
        movie: Movie
    ) {
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
        Glide.with(this)
            .load(movie.backdrop)
            .apply(imageOption)
            .into(view.findViewById(R.id.iv_poster))

        if (movie.actors.isNotEmpty()) {
            actorsAdapter.updateActors(movie.actors)
        } else {
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

    override fun onDestroyView() {
        scope.cancel()
        super.onDestroyView()
    }

    interface MovieDetailsNavigationListener {
        fun onBackPressed()
    }

    companion object {
        private const val MOVIE_ID = "movie_id"
        private const val ERROR_TAG = "COROUTINE_ERROR"

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