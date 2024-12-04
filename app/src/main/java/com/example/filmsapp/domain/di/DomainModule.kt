package com.example.filmsapp.domain.di

import com.example.filmsapp.domain.usecases.film.GetFilteredFilmsUseCase
import com.example.filmsapp.domain.usecases.film.GetPopularFilmsUseCase
import com.example.filmsapp.domain.usecases.film.SearchFilmsUseCase
import com.example.filmsapp.domain.usecases.film.UpdateFilmFavoriteStatusUseCase
import com.example.filmsapp.domain.usecases.genre.GetGenresUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::GetFilteredFilmsUseCase)
    factoryOf(::GetPopularFilmsUseCase)
    factoryOf(::SearchFilmsUseCase)
    factoryOf(::UpdateFilmFavoriteStatusUseCase)
    factoryOf(::GetGenresUseCase)
}