package com.example.filmsapp.presentation.screens.main.contract

import androidx.compose.runtime.Immutable
import com.example.filmsapp.domain.models.genre.Genre
import com.example.filmsapp.presentation.screens.main.models.FilmListItem

@Immutable
data class MainState(
    val films: List<FilmListItem> = emptyList(),
    val searchMode: Boolean = false,
    val searchQuery: String = "",
    val selectedGenre: Int? = null,
    val genres: List<Genre> = emptyList(),
    val isLoading: Boolean = false
)