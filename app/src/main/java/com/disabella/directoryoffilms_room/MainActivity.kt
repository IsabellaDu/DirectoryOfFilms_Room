package com.disabella.directoryoffilms_room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.disabella.directoryoffilms_room.db.AppDataBase
import com.disabella.directoryoffilms_room.db.Film
import com.disabella.directoryoffilms_room.db.FilmDao
import com.disabella.directoryoffilms_room.db.defaultFilms
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

lateinit var filmDao: FilmDao

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java,
            "my_app_database"
        ).build()
        filmDao = db.filmDao()

    }

    override fun onResume() {
        super.onResume()

        val buttonOpenDirectory = findViewById<Button>(R.id.open)
        val buttonSave = findViewById<Button>(R.id.save)
        val buttonClear = findViewById<Button>(R.id.clear)

        val name = findViewById<EditText>(R.id.name)
        val releaseYear = findViewById<EditText>(R.id.releaseYear)
        val producer = findViewById<EditText>(R.id.producer)
        val description = findViewById<EditText>(R.id.description)

        buttonSave.setOnClickListener {
            if (name.text.isNotEmpty()
                && releaseYear.text.isNotEmpty()
                && producer.text.isNotEmpty()
                && description.text.isNotEmpty()
            ) {
                val addFilm = Film(
                    name.text.toString(),
                    releaseYear.text.toString(),
                    producer.text.toString(),
                    description.text.toString()
                )
                lifecycleScope.launch(Dispatchers.IO) {
                    filmDao.insertAll(addFilm)
                }
            } else {
                Toast.makeText(this, "input field cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        buttonOpenDirectory.setOnClickListener {
            startActivity(Intent(this, DirectoryActivity::class.java))
        }

        buttonClear.setOnClickListener {
            name.text.clear()
            releaseYear.text.clear()
            producer.text.clear()
            description.text.clear()
        }

    }
}