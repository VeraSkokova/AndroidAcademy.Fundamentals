package ru.skokova.android_academy.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import ru.skokova.android_academy.R
import ru.skokova.android_academy.data.model.Movie
import ru.skokova.android_academy.ui.FragmentMoviesList

class AdapterMovies(
    private val clickListener: FragmentMoviesList.MovieClickListener?,
    private val requestBuilder: RequestManager
) :
    RecyclerView.Adapter<MovieViewHolder>(), ListPreloader.PreloadModelProvider<Movie> {
    private var movies = listOf<Movie>()
    private lateinit var baseUrl: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies.getOrNull(position), baseUrl)
        holder.itemView.setOnClickListener {
            movies.getOrNull(position)?.id?.let { pos ->
                clickListener?.onClick(pos)
            }
        }
    }

    override fun getItemCount(): Int = movies.size

    override fun getItemId(position: Int): Long {
        return movies.getOrNull(position)?.id?.toLong() ?: 0
    }

    fun updateMovies(movies: List<Movie>, baseUrl: String) {
        this.movies = movies
        this.baseUrl = baseUrl
        notifyDataSetChanged()
    }

    override fun getPreloadItems(position: Int): List<Movie> {
        val element = movies.getOrNull(position)
        element?.let {
            return listOf(element)
        }
        return emptyList()
    }

    override fun getPreloadRequestBuilder(item: Movie): RequestBuilder<*> {
        return requestBuilder
            .load(item.poster)
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

    fun bind(movie: Movie?, baseUrl: String) {
        movie?.let {
            Glide.with(itemView.context)
                .load(baseUrl + movie.poster)
                .apply(imageOption)
                .into(poster)
            rating.rating = movie.ratings
            reviews.text = itemView.context.resources.getQuantityString(
                R.plurals.movie_reviews,
                movie.numberOfRatings,
                movie.numberOfRatings
            )
            tags.text = movie.genres
            like.setImageResource(R.drawable.ic_like)
            pg.text = itemView.resources.getString(R.string.pg, movie.minimumAge)
            name.text = movie.title
        }
    }

    companion object {
        private val imageOption = RequestOptions()
            .placeholder(R.drawable.ic_baseline_movie)
            .fallback(R.drawable.ic_baseline_movie)
            .centerCrop()
    }
}
