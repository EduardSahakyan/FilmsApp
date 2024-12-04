package com.example.filmsapp.presentation.screens.filmdetails.models

data class FilmDetails(
    val id: Int,
    val genres: List<String>,
    val title: String,
    val description: String,
    val releaseDate: String,
    val voteAvg: Double,
    val isFavorite: Boolean
)