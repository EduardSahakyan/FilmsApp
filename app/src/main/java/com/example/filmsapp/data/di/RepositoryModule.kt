package com.example.filmsapp.data.di

import com.example.filmsapp.data.repositories.FilmsRepositoryImpl
import com.example.filmsapp.domain.repositories.FilmsRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {

    singleOf(::FilmsRepositoryImpl) { bind<FilmsRepository>() }

}