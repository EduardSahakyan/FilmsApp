package com.example.filmsapp.domain.repositories

import com.example.filmsapp.domain.models.film.Film
import com.example.filmsapp.domain.models.genre.Genre
import com.example.filmsapp.domain.resource.Resource
import kotlinx.coroutines.flow.Flow

interface FilmsRepository {

    fun getPopularFilms(page: Int): Flow<Resource<List<Film>>>

    fun searchFilms(query: String): Flow<Resource<List<Film>>>

    fun getFilteredFilms(genreId: Int, page: Int): Flow<Resource<List<Film>>>

    fun getGenres(): Flow<Resource<List<Genre>>>

    suspend fun updateFavoriteStatus(filmId: Int, isFavorite: Boolean)

}