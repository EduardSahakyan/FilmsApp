package com.example.filmsapp.data.di

import androidx.room.Room
import com.example.filmsapp.data.local.AppDatabase
import com.example.filmsapp.data.local.daos.FilmsDao
import com.example.filmsapp.data.local.daos.GenresDao
import org.koin.dsl.module

val localModule = module {

    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java, "app.db"
        ).build()
    }

    single<FilmsDao> {
        get<AppDatabase>().filmsDao()
    }

    single<GenresDao> {
        get<AppDatabase>().genresDao()
    }

}