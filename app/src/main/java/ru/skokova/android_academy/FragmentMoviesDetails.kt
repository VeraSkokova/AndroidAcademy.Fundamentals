package ru.skokova.android_academy

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.skokova.android_academy.actor.ActorGenerator
import ru.skokova.android_academy.actor.ActorListDecoration
import ru.skokova.android_academy.actor.AdapterActors
import ru.skokova.android_academy.movie.MovieGenerator

class FragmentMoviesDetails : Fragment() {
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
            MovieGenerator.getMovieById(it)?.let { movie ->
                view.findViewById<TextView>(R.id.tv_movie_name).text = movie.name
                view.findViewById<TextView>(R.id.tv_pg).text = movie.pg
                view.findViewById<TextView>(R.id.tv_tags).text = movie.tags
                view.findViewById<RatingBar>(R.id.rating).rating = movie.rating
                view.findViewById<TextView>(R.id.tv_reviews).text =
                    context?.resources?.getQuantityString(
                        R.plurals.movie_reviews,
                        movie.reviews,
                        movie.reviews
                    )
                view.findViewById<TextView>(R.id.tv_description).text = movie.description
                view.findViewById<ImageView>(R.id.iv_poster)
                    .setImageResource(movie.detailsPoster)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        actorsAdapter.updateActors(ActorGenerator().getActorsById(arguments?.getInt(MOVIE_ID) ?: 0))
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

        fun newInstance(movieId: Int): FragmentMoviesDetails {
            val fragment = FragmentMoviesDetails()
            val args = Bundle()
            args.putInt(MOVIE_ID, movieId)
            fragment.arguments = args
            return fragment
        }
    }
}