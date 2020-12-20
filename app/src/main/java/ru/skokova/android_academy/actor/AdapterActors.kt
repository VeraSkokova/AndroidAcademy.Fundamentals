package ru.skokova.android_academy.actor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.skokova.android_academy.R
import ru.skokova.android_academy.data.Actor

class AdapterActors : RecyclerView.Adapter<ActorViewHolder>() {
    private var actors: List<Actor> =
        listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        return ActorViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_actor, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.bind(actors.getOrNull(position))
    }

    override fun getItemCount() = actors.size

    fun updateActors(actors: List<Actor>) {
        this.actors = actors
        notifyDataSetChanged()
    }
}

class ActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val photo = itemView.findViewById<ImageView>(R.id.img_actor)
    private val name = itemView.findViewById<TextView>(R.id.tv_actor)

    fun bind(actor: Actor?) {
        actor?.let { it ->
            name.text = it.name
            photo.visibility = View.VISIBLE
            Glide.with(itemView.context)
                .load(actor.picture)
                .apply(imageOption)
                .into(photo)
        }
    }

    private val imageOption = RequestOptions()
        .placeholder(R.drawable.ic_baseline_movie)
        .fallback(R.drawable.ic_baseline_movie)
        .centerCrop()
}