package com.example.filmsapp.data.repositories

import com.example.filmsapp.data.local.daos.FilmsDao
import com.example.filmsapp.data.local.daos.GenresDao
import com.example.filmsapp.data.mappers.toEntities
import com.example.filmsapp.data.mappers.toFilms
import com.example.filmsapp.data.mappers.toGenres
import com.example.filmsapp.data.network.ApiService
import com.example.filmsapp.domain.models.film.Film
import com.example.filmsapp.domain.models.genre.Genre
import com.example.filmsapp.domain.repositories.FilmsRepository
import com.example.filmsapp.domain.resource.Resource
import kotlinx.coroutines.flow.Flow

class FilmsRepositoryImpl(
    private val apiService: ApiService,
    private val filmsDao: FilmsDao,
    private val genresDao: GenresDao
) : FilmsRepository {

    override fun getPopularFilms(page: Int): Flow<Resource<List<Film>>> = safeApiCallWithCaching(
        apiCall = { apiService.getPopularFilms(page) },
        dtoMapper = { dto -> dto.toFilms() },
        entityMapper = { entity -> entity.toFilms() },
        saveToCache = { data -> data?.let { filmsDao.insertFilms(data.toEntities()) } },
        loadFromCache = { filmsDao.getPopularFilms(offset = getOffset(page), perPage = PER_PAGE) },
        alwaysLoadFromCache = true
    )

    override fun searchFilms(query: String): Flow<Resource<List<Film>>> = safeApiCallWithCaching(
        apiCall = { apiService.searchFilms(query) },
        dtoMapper = { dto -> dto.toFilms() },
        entityMapper = { entity -> entity.toFilms() },
        saveToCache = { data -> data?.let { filmsDao.insertFilms(data.toEntities()) } },
        loadFromCache = { filmsDao.searchFilms(query) }
    )

    override fun getFilteredFilms(genreId: Int, page: Int): Flow<Resource<List<Film>>> = safeApiCallWithCaching(
        apiCall = { apiService.getFilteredFilms(genreId, page) },
        dtoMapper = { dto -> dto.toFilms() },
        entityMapper = { entity -> entity.toFilms() },
        saveToCache = { data -> data?.let { filmsDao.insertFilms(data.toEntities()) } },
        loadFromCache = { filmsDao.getFilteredFilms(genreId = genreId, offset = getOffset(page), perPage = PER_PAGE) }
    )

    override fun getGenres(): Flow<Resource<List<Genre>>> = safeApiCallWithCaching(
        apiCall = { apiService.getGenres() },
        dtoMapper = { it.toGenres() },
        entityMapper = { entity -> entity.toGenres() },
        saveToCache = { data -> data?.let { genresDao.insertGenres(data.toEntities()) } },
        loadFromCache = { genresDao.getGenres() }
    )

    override suspend fun updateFavoriteStatus(filmId: Int, isFavorite: Boolean) {
        filmsDao.updateFavoriteStatus(filmId, isFavorite)
    }

    private fun getOffset(page: Int) = (page - 1) * PER_PAGE

    companion object {
        private const val PER_PAGE = 20
    }

}