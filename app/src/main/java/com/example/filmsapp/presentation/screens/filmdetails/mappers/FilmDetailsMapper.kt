package com.example.filmsapp.presentation.screens.filmdetails.mappers

import com.example.filmsapp.domain.models.film.Film
import com.example.filmsapp.domain.models.genre.Genre
import com.example.filmsapp.presentation.screens.filmdetails.models.FilmDetails

fun Film.toDetails(genres: List<Genre>) = FilmDetails(
    id = id,
    genres = genres.filter { it.id in advancedInfo.genreIds }.map { it.name },
    title = title,
    description = description,
    releaseDate = advancedInfo.releaseDate,
    voteAvg = advancedInfo.voteAvg,
    isFavorite = advancedInfo.isFavorite
)