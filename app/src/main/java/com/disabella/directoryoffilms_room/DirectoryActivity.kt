package com.disabella.directoryoffilms_room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.disabella.directoryoffilms_room.db.Film
import com.disabella.directoryoffilms_room.db.defaultFilms
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private lateinit var adapter: RecyclerAdapter

class DirectoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_directory)


        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RecyclerAdapter(ArrayList())
        recyclerView.adapter = adapter

        val defaultFilms: List<Film> = defaultFilms
        recyclerView.adapter = RecyclerAdapter(defaultFilms)

        lifecycleScope.launch(Dispatchers.Main)
        {
            val films: List<Film> = filmDao.selectAll()
            recyclerView.adapter = RecyclerAdapter(films)
        }
    }
}