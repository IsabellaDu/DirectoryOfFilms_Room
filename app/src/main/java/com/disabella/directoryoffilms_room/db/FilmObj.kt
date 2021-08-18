package com.disabella.directoryoffilms_room.db

import androidx.room.Room
import com.disabella.directoryoffilms_room.App

object FilmObj {
    var filmDao: FilmDao

    init {
        val db = Room.databaseBuilder(
            App.appContext,
            AppDataBase::class.java,
            "my_app_database"
        ).build()
        filmDao = db.filmDao()
    }
}