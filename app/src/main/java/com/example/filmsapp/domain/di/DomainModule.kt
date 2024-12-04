package com.example.filmsapp.domain.di

import com.example.filmsapp.domain.usecases.film.GetFilmByIdUseCase
import com.example.filmsapp.domain.usecases.film.GetFilmsUseCase
import com.example.filmsapp.domain.usecases.film.SearchFilmsUseCase
import com.example.filmsapp.domain.usecases.film.UpdateFilmFavoriteStatusUseCase
import com.example.filmsapp.domain.usecases.genre.GetGenresUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::GetFilmsUseCase)
    factoryOf(::SearchFilmsUseCase)
    factoryOf(::UpdateFilmFavoriteStatusUseCase)
    factoryOf(::GetGenresUseCase)
    factoryOf(::GetFilmByIdUseCase)
}