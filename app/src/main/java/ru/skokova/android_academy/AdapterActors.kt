package ru.skokova.android_academy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterActors : RecyclerView.Adapter<ActorViewHolder>() {
    private var actors = listOf<Actor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        return ActorViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_actor, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.bind(actors[position])
    }

    override fun getItemCount() = actors.size

    fun bindActors(actors: List<Actor>) {
        this.actors = actors
        notifyDataSetChanged()
    }
}

class ActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val photo = itemView.findViewById<ImageView>(R.id.img_actor)
    private val name = itemView.findViewById<TextView>(R.id.tv_actor)

    fun bind(actor: Actor) {
        name.text = actor.name
        photo.setImageResource(actor.photo)
    }
}