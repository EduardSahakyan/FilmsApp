package com.example.filmsapp.presentation.screens.main.contract

sealed interface MainIntent {

    data object LoadNextPage : MainIntent

    data class SetGenre(val id: Int) : MainIntent

    data object ToggleSearchMode : MainIntent

    data class ChangeSearchQuery(val query: String) : MainIntent

    data object Refresh : MainIntent

    data class FilmClicked(val id: Int) : MainIntent

}