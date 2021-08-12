package com.disabella.directoryoffilms_room.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FilmDao {

    @Insert
    suspend fun insertAll(vararg films: Film)

    @Query("SELECT * FROM Film")
    suspend fun selectAll(): List<Film>
}