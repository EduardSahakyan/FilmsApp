package com.example.filmsapp.data.mappers

import com.example.filmsapp.data.local.entities.GenreEntity
import com.example.filmsapp.data.network.dtos.genre.GenreDto
import com.example.filmsapp.data.network.dtos.genre.GenresDto
import com.example.filmsapp.domain.models.genre.Genre

fun GenresDto.toEntities() = genres.map { it.toEntity() }

fun GenreDto.toEntity() = GenreEntity(id = id, name = name)

fun List<GenreEntity>.toGenres() = map { it.toGenre() }

fun GenreEntity.toGenre() = Genre(id = id, name = name)

fun GenresDto.toGenres() = genres.map { it.toGenre() }

fun GenreDto.toGenre() = Genre(id = id, name = name)