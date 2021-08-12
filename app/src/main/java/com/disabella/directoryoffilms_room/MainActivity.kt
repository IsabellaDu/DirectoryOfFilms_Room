package com.disabella.directoryoffilms_room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.disabella.directoryoffilms_room.db.AppDataBase
import com.disabella.directoryoffilms_room.db.Film
import com.disabella.directoryoffilms_room.db.FilmDao
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


        val buttonLoad = findViewById<Button>(R.id.load)
        val buttonSave = findViewById<Button>(R.id.save)
        val textView = findViewById<TextView>(R.id.textView)

        buttonSave.setOnClickListener {
            val first = Film(
                "Shrek",
                2001,
                "Aron Warner",
                "Shrek is an anti-social and highly-territorial green ogre who loves the solitude of his swamp."
            )
            lifecycleScope.launch(Dispatchers.IO) {
                filmDao.insertAll(first)
            }
        }

        buttonLoad.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                val films: List<Film> = filmDao.selectAll()
                Log.i("my_tag", "$films")
                textView.text = films[0].description
            }
        }
    }
}