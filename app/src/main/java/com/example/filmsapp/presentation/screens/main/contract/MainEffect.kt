package com.example.filmsapp.presentation.screens.main.contract

sealed interface MainEffect {

    data class NavigateToDetails(val filmId: Int) : MainEffect

    data object ScrollToTop : MainEffect

    data object ShowConnectionErrorToast : MainEffect

}