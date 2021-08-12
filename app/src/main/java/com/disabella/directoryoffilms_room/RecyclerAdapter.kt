package com.disabella.directoryoffilms_room

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.disabella.directoryoffilms_room.db.Film
import kotlin.random.Random

class RecyclerAdapter(private val films: List<Film>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: FrameLayout = view.findViewById(R.id.icon)
        val name: TextView = view.findViewById(R.id.name)
        val releaseYear: TextView = view.findViewById(R.id.releaseYear)
        val producer: TextView = view.findViewById(R.id.producer)
        val description: TextView = view.findViewById(R.id.description)


        init {
            view.setOnClickListener {
                Toast.makeText(view.context, "${name.text} clicked", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int = films.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_view, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        films[position].apply {
            viewHolder.icon.setBackgroundColor(randomColor())
            viewHolder.name.text = films[position].name
            viewHolder.releaseYear.text = films[position].releaseYear
            viewHolder.producer.text = films[position].producer
            viewHolder.description.text = films[position].description
        }
    }
}

fun randomColor(): Int =
    Color.argb(
        255,
        Random.nextInt(256),
        Random.nextInt(256),
        Random.nextInt(256)
    )