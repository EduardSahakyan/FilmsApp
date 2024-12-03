package com.example.filmsapp.data.local.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.filmsapp.data.local.entities.GenreEntity

@Dao
interface GenresDao {

    @Upsert
    suspend fun insertGenres(genres: List<GenreEntity>)

    @Query("SELECT * FROM genres")
    suspend fun getGenres(): List<GenreEntity>

}