package com.example.filmsapp.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.filmsapp.presentation.navigation.AppNavGraph
import com.example.filmsapp.presentation.theme.FilmsAppTheme
import org.koin.androidx.compose.KoinAndroidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FilmsAppTheme {
                KoinAndroidContext {
                    AppNavGraph()
                }
            }
        }
    }
}
