package com.example.filmsapp.data.di

import org.koin.dsl.module

val dataModule = module {
    includes(networkModule, localModule, repositoryModule)
}