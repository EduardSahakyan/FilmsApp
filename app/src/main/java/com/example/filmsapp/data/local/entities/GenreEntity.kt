package com.example.filmsapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("genres")
class GenreEntity(
    @PrimaryKey
    val id: Int,
    val name: String
)