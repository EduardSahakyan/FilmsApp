package com.example.filmsapp.presentation.screens.filmdetails.contract

import androidx.compose.runtime.Immutable
import com.example.filmsapp.presentation.screens.filmdetails.models.FilmDetails

@Immutable
data class FilmDetailsState(
    val film: FilmDetails? = null
)