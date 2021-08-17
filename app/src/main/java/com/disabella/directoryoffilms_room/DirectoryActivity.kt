package com.disabella.directoryoffilms_room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.contains
import androidx.core.view.get
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.disabella.directoryoffilms_room.db.Film
import com.disabella.directoryoffilms_room.db.FilmDao
import com.disabella.directoryoffilms_room.db.defaultFilms
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

private val adapter = RecyclerAdapter(ArrayList(defaultFilms))

lateinit var films: ArrayList<Film>
var tempFilms: ArrayList<Film> = arrayListOf<Film>()

class DirectoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_directory)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        lifecycleScope.launch(Dispatchers.Main)
        {
            adapter.addItems(filmDao.selectAll())
        }

        val searchView = findViewById<SearchView>(R.id.searchView)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newText: String?): Boolean {
                lifecycleScope.launch(Dispatchers.IO) {
                    films = filmDao.selectAll() as ArrayList<Film>
                    if (newText != null) {
                        searchFilm(newText)
                    }
                    withContext(Dispatchers.Main)
                    {
                        adapter.addItems(tempFilms)
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                lifecycleScope.launch(Dispatchers.IO) {
                    films = filmDao.selectAll() as ArrayList<Film>
                    if (newText != null) {
                        searchFilm(newText)
                    }
                    withContext(Dispatchers.Main)
                    {
                        adapter.addItems(tempFilms)
                    }
                }
                return false
            }
        })

        searchView.setOnCloseListener(object : androidx.appcompat.widget.SearchView.OnCloseListener,
            SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                adapter.addItems(films)
                return false
            }
        })
    }
}

private fun searchFilm(searchText: String): ArrayList<Film> {
    if (searchText.toLowerCase(Locale.getDefault()).isNotEmpty()) {
        tempFilms.clear()
        films.forEach {
            if (it.name?.toLowerCase(Locale.getDefault())!!.contains(searchText)) {
                tempFilms.add(
                    Film(
                        it.name,
                        it.releaseYear,
                        it.producer,
                        it.description
                    )
                )
            }
        }
    } else {
        tempFilms.clear()
        tempFilms.addAll(films)
    }
    return tempFilms
}
