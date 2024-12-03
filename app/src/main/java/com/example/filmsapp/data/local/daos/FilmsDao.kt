package com.example.filmsapp.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.filmsapp.data.local.entities.FilmEntity

@Dao
interface FilmsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFilms(films: List<FilmEntity>)

    @Query("SELECT * FROM films ORDER BY popularity DESC LIMIT :perPage OFFSET :offset")
    suspend fun getPopularFilms(offset: Int, perPage: Int): List<FilmEntity>

    @Query("SELECT * FROM films WHERE ',' || genreIds || ',' LIKE '%,' || :genreId || ',%' ORDER BY popularity DESC LIMIT :perPage OFFSET :offset")
    suspend fun getFilteredFilms(genreId: Int, offset: Int, perPage: Int): List<FilmEntity>

    @Query("SELECT * FROM films WHERE title LIKE '%' || :query || '%' COLLATE NOCASE ORDER BY popularity DESC")
    suspend fun searchFilms(query: String): List<FilmEntity>

    @Query("UPDATE films SET isFavorite = :isFavorite WHERE id = :filmId")
    suspend fun updateFavoriteStatus(filmId: Int, isFavorite: Boolean)

}