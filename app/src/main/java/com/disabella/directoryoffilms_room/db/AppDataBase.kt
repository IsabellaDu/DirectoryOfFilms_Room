package com.disabella.directoryoffilms_room.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Film::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}