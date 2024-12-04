package com.example.filmsapp.presentation.screens.filmdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.filmsapp.R
import com.example.filmsapp.presentation.screens.filmdetails.contract.FilmDetailsEffect
import com.example.filmsapp.presentation.screens.filmdetails.contract.FilmDetailsIntent
import com.example.filmsapp.presentation.screens.filmdetails.uicomponents.GenreChip
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmDetailsScreen(
    viewModel: FilmDetailsViewModel = koinViewModel(),
    filmId: Int,
    navigateUp: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.handleIntent(FilmDetailsIntent.GetFilmDetails(filmId))
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                FilmDetailsEffect.NavigateBack -> navigateUp()
            }
        }
    }

    val state = viewModel.state.collectAsState()

    state.value.film?.let { filmDetails ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(R.string.details))
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { viewModel.handleIntent(FilmDetailsIntent.BackClicked) }
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = filmDetails.title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyRow(
                    modifier = Modifier.padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filmDetails.genres) { genre ->
                        GenreChip(text = genre)
                    }
                }

                Text(
                    text = stringResource(R.string.description),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = filmDetails.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.release_date, filmDetails.releaseDate),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = stringResource(R.string.rating, filmDetails.voteAvg),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Button(
                    onClick = { viewModel.handleIntent(FilmDetailsIntent.ToggleFavoritesStatus) },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = if (filmDetails.isFavorite)
                            stringResource(R.string.unmark_favorite)
                        else
                            stringResource(R.string.mark_favorite)
                    )
                }
            }
        }
    }

}

