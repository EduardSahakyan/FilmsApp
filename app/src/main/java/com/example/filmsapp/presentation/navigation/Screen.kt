package com.example.filmsapp.presentation.navigation

sealed class Screen(val route: String) {

    data object Main : Screen(ROUTE_MAIN)

    data object FilmDetails : Screen(ROUTE_DETAILS)

    companion object {
        private const val ROUTE_MAIN = "route_main"
        private const val ROUTE_DETAILS = "route_details"

        const val ARG_FILM_ID = "/{filmId}"
        const val FILM_ID = "filmId"
    }

}