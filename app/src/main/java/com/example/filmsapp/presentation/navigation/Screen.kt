package com.example.filmsapp.presentation.navigation

sealed class Screen(val route: String) {

    data object Main : Screen(ROUTE_MAIN)

    companion object {
        private const val ROUTE_MAIN = "route_main"
    }

}