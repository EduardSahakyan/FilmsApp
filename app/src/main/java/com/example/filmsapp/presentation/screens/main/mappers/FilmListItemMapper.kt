package com.example.filmsapp.presentation.screens.main.mappers

import com.example.filmsapp.domain.models.film.Film
import com.example.filmsapp.presentation.screens.main.models.FilmListItem

fun List<Film>.toListItems() = map { it.toListItem() }

fun Film.toListItem() = FilmListItem(id = id, title = title, description = description)