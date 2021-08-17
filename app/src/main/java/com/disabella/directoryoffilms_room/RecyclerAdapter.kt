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

class RecyclerAdapter(private val films: MutableList<Film>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private val items = ArrayList<Film>(films)
    private val filteredItems = ArrayList<Film>(films)

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

    override fun getItemCount(): Int = filteredItems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_view, parent, false)
        return ViewHolder(view)
    }

    fun addItems(items: List<Film>) {
        this.items.addAll(items)
        filter(null)
    }

    fun filter(filter: String?) {
        filteredItems.clear()
        val result = if (filter.isNullOrBlank()) {
            items
        } else {
            items.filter { it.name?.contains(filter, ignoreCase = true) ?: false }
        }
        filteredItems.addAll(result)
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        filteredItems[position].apply {
            viewHolder.icon.setBackgroundColor(randomColor())
            viewHolder.name.text = filteredItems[position].name
            viewHolder.releaseYear.text = filteredItems[position].releaseYear
            viewHolder.producer.text = filteredItems[position].producer
            viewHolder.description.text = filteredItems[position].description
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