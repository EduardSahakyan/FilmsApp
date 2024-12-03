package com.example.filmsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.filmsapp.data.local.daos.FilmsDao
import com.example.filmsapp.data.local.daos.GenresDao
import com.example.filmsapp.data.local.entities.FilmEntity
import com.example.filmsapp.data.local.entities.GenreEntity

@Database(entities = [FilmEntity::class, GenreEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun filmsDao(): FilmsDao

    abstract fun genresDao(): GenresDao

}