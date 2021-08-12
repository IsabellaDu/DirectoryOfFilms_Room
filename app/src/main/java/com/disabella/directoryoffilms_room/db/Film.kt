package com.disabella.directoryoffilms_room.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Film(
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "release_year")
    val releaseYear: Int?,
    @ColumnInfo(name = "producer")
    val producer: String?,
    @ColumnInfo(name = "description")
    val description: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}