package com.example.filmsapp.data.mappers

import com.example.filmsapp.data.local.entities.FilmEntity
import com.example.filmsapp.data.network.dtos.film.FilmResultDto
import com.example.filmsapp.data.network.dtos.film.FilmsDto
import com.example.filmsapp.domain.models.film.Film
import com.example.filmsapp.domain.models.film.FilmAdvancedInfo

fun FilmsDto.toFilms() = results.map { it.toFilm() }

fun FilmResultDto.toFilm() = Film(
    id = id,
    title = title,
    description = description,
    advancedInfo = FilmAdvancedInfo(
        genreIds = genreIds,
        releaseDate = releaseDate,
        voteAvg = voteAvg,
        isFavorite = false
    )
)

fun FilmsDto.toEntities() = results.map { it.toEntity() }

fun FilmResultDto.toEntity() = FilmEntity(
    id = id,
    genreIds = genreIds.joinToString(","),
    title = title,
    description = description,
    releaseDate = releaseDate,
    voteAvg = voteAvg,
    isFavorite = false,
    popularity = popularity
)

fun List<FilmEntity>.toFilms() = map { it.toFilm() }

fun FilmEntity.toFilm() = Film(
    id = id,
    title = title,
    description = description,
    advancedInfo = FilmAdvancedInfo(
        genreIds = genreIds.split(",").filter { it.isNotBlank() }.map { it.toInt() },
        releaseDate = releaseDate,
        voteAvg = voteAvg,
        isFavorite = isFavorite
    )
)