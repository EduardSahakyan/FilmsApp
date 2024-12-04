package com.example.filmsapp.presentation.screens.filmdetails.contract

sealed interface FilmDetailsEffect {

    data object NavigateBack : FilmDetailsEffect

}