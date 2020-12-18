package ru.skokova.android_academy.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.skokova.android_academy.FragmentMoviesList
import ru.skokova.android_academy.R

class AdapterMovies(private val clickListener: FragmentMoviesList.MovieClickListener?) : RecyclerView.Adapter<MovieViewHolder>() {
    private var movies = listOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
        holder.itemView.setOnClickListener { clickListener?.onClick(position) }
    }

    override fun getItemCount(): Int = movies.size

    fun updateMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }
}

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val poster = itemView.findViewById<ImageView>(R.id.iv_movie)
    private val rating = itemView.findViewById<RatingBar>(R.id.rating)
    private val reviews = itemView.findViewById<TextView>(R.id.tv_reviews)
    private val tags = itemView.findViewById<TextView>(R.id.tv_tags)
    private val like = itemView.findViewById<ImageView>(R.id.iv_like)
    private val pg = itemView.findViewById<TextView>(R.id.tv_pg)
    private val name = itemView.findViewById<TextView>(R.id.tv_movie_name)
    private val duration = itemView.findViewById<TextView>(R.id.tv_duration)

    fun bind(movie: Movie) {
        poster.setImageResource(movie.listPoster)
        rating.rating = movie.rating
        reviews.text = itemView.context.resources.getQuantityString(R.plurals.movie_reviews, movie.reviews, movie.reviews)
        tags.text = movie.tags
        like.setImageResource(movie.like)
        pg.text = movie.pg
        name.text = movie.name
        duration.text = itemView.context.getString(R.string.movie_duration, movie.duration)
    }
}
