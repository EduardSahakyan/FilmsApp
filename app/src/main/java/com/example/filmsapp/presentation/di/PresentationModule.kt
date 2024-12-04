package com.example.filmsapp.presentation.di

import com.example.filmsapp.presentation.screens.main.MainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::MainViewModel)
}