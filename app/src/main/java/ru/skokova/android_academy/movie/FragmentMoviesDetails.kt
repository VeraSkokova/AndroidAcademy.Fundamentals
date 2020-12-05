package ru.skokova.android_academy.movie

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
import ru.skokova.android_academy.AdapterActors
import ru.skokova.android_academy.Metrics
import ru.skokova.android_academy.R
import ru.skokova.android_academy.actor.ActorGenerator
import ru.skokova.android_academy.actor.ActorListDecoration

class FragmentMoviesDetails : Fragment() {
    private val actorsAdapter = AdapterActors()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.tv_back)
            .setOnClickListener { requireActivity().onBackPressed() }
        view.findViewById<ImageView>(R.id.img_back)
            .setOnClickListener { requireActivity().onBackPressed() }
        view.findViewById<RecyclerView>(R.id.rv_actors).apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = actorsAdapter
            addItemDecoration(ActorListDecoration(Metrics.dpToPx(8)))
        }

        val movieId = arguments?.getInt(MOVIE_ID)
        val movie = movieId?.let { MovieGenerator(context).getMovieById(it) }
        view.findViewById<TextView>(R.id.tv_movie_name).text = movie?.name
        view.findViewById<TextView>(R.id.tv_pg).text = movie?.pg
        view.findViewById<TextView>(R.id.tv_tags).text = movie?.tags
        view.findViewById<RatingBar>(R.id.rating).rating = movie?.rating ?: 0f
        view.findViewById<TextView>(R.id.tv_reviews).text = movie?.reviews
        view.findViewById<TextView>(R.id.tv_description).text = movie?.description
        view.findViewById<ImageView>(R.id.iv_poster).setImageResource(movie?.detailsPoster ?: View.NO_ID)
    }

    override fun onStart() {
        super.onStart()
        actorsAdapter.bindActors(ActorGenerator().getActorsById(arguments?.getInt(MOVIE_ID) ?: 0))
    }

    companion object {
        val MOVIE_ID = "movie_id"

        fun newInstance(movieId: Int): FragmentMoviesDetails {
            val fragment = FragmentMoviesDetails()
            val args = Bundle()
            args.putInt(MOVIE_ID, movieId)
            fragment.arguments = args
            return fragment
        }
    }
}