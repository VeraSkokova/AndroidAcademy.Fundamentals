package ru.skokova.android_academy.actor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.skokova.android_academy.R

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
            photo.setImageResource(it.photo)
        }
    }
}