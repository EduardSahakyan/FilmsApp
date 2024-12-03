package com.example.filmsapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("films")
class FilmEntity(
    @PrimaryKey
    val id: Int,
    val genreIds: String,
    val title: String,
    val description: String,
    val releaseDate: String,
    val voteAvg: Double,
    val popularity: Double,
    val isFavorite: Boolean
)