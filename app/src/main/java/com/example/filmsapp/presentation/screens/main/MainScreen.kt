package com.example.filmsapp.presentation.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.filmsapp.presentation.screens.main.contract.MainIntent
import com.example.filmsapp.presentation.screens.main.uicomponents.AnimatedSearchBar
import com.example.filmsapp.presentation.screens.main.uicomponents.FilmListItemView
import com.example.filmsapp.presentation.screens.main.uicomponents.GenreChip
import com.example.filmsapp.presentation.screens.main.uicomponents.PullToRefreshBox
import com.example.filmsapp.presentation.screens.main.uicomponents.SearchButton
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = koinViewModel()
) {

    val state = viewModel.state.collectAsState()

    val filmsListState = rememberLazyListState()
    val isAtBottom = !filmsListState.canScrollForward

    LaunchedEffect(isAtBottom){
        if (isAtBottom) {
            viewModel.handleIntent(MainIntent.LoadNextPage)
        }
    }

    PullToRefreshBox(
        isRefreshing = state.value.isLoading,
        onRefresh = { viewModel.handleIntent(MainIntent.Refresh) },
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        content = {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                AnimatedSearchBar(
                    isSearchMode = state.value.searchMode,
                    toggleSearchMode = { viewModel.handleIntent(MainIntent.ToggleSearchMode) },
                    searchQuery = state.value.searchQuery,
                    onSearchQueryChanged = { viewModel.handleIntent(MainIntent.ChangeSearchQuery(it)) }
                )
                if (state.value.searchMode.not()) {
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SearchButton(
                            onClick = { viewModel.handleIntent(MainIntent.ToggleSearchMode) },
                        )
                        LazyRow {
                            items(
                                items = state.value.genres,
                                key = { it.id }
                            ) { genre ->
                                GenreChip(
                                    genre = genre.name,
                                    isSelected = genre.id == state.value.selectedGenre,
                                    onClick = { viewModel.handleIntent(MainIntent.SetGenre(genre.id)) }
                                )
                            }
                        }
                    }
                }
                HorizontalDivider()
                LazyColumn(
                    state = filmsListState
                ) {
                    items(
                        items = state.value.films,
                        key = { it.id },
                    ) { film ->
                        FilmListItemView(
                            film = film,
                            onClick = {}
                        )
                    }
                }
            }
        }
    )

}
