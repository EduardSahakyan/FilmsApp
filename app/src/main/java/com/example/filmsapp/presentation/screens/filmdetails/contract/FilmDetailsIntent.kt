package com.example.filmsapp.presentation.screens.filmdetails.contract

sealed interface FilmDetailsIntent {

    data class GetFilmDetails(val id: Int) : FilmDetailsIntent

    data object ToggleFavoritesStatus : FilmDetailsIntent

    data object BackClicked : FilmDetailsIntent

}