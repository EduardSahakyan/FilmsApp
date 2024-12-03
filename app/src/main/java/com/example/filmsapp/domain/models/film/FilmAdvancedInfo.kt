package com.example.filmsapp.domain.models.film

class FilmAdvancedInfo(
    val genreIds: List<Int>,
    val releaseDate: String,
    val voteAvg: Double,
    val isFavorite: Boolean
)