package com.example.filmsapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.filmsapp.presentation.screens.filmdetails.FilmDetailsScreen
import com.example.filmsapp.presentation.screens.main.MainScreen

@Composable
fun AppNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(
            route = Screen.Main.route
        ) {
            MainScreen(
                navigateToDetails = {
                    navController.navigate(route = "${Screen.FilmDetails.route}/$it")
                }
            )
        }

        composable(
            route = Screen.FilmDetails.route + Screen.ARG_FILM_ID,
            arguments = listOf(
                navArgument(
                    name = Screen.FILM_ID
                ) {
                    nullable = false
                    type = NavType.IntType
                }
            )
        ) {
            val filmId = it.arguments?.getInt(Screen.FILM_ID) ?: 0
            FilmDetailsScreen(
                filmId = filmId,
                navigateUp = { navController.navigateUp() }
            )
        }

    }

}