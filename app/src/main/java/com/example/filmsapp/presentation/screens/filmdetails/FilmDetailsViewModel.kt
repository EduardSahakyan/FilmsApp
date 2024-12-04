package com.example.filmsapp.presentation.screens.filmdetails

import androidx.lifecycle.viewModelScope
import com.example.filmsapp.domain.models.film.Film
import com.example.filmsapp.domain.models.genre.Genre
import com.example.filmsapp.domain.resource.Resource
import com.example.filmsapp.domain.usecases.film.GetFilmByIdUseCase
import com.example.filmsapp.domain.usecases.film.UpdateFilmFavoriteStatusUseCase
import com.example.filmsapp.domain.usecases.genre.GetGenresUseCase
import com.example.filmsapp.presentation.base.BaseViewModel
import com.example.filmsapp.presentation.screens.filmdetails.contract.FilmDetailsEffect
import com.example.filmsapp.presentation.screens.filmdetails.contract.FilmDetailsIntent
import com.example.filmsapp.presentation.screens.filmdetails.contract.FilmDetailsState
import com.example.filmsapp.presentation.screens.filmdetails.mappers.toDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FilmDetailsViewModel(
    private val getGenresUseCase: GetGenresUseCase,
    private val getFilmByIdUseCase: GetFilmByIdUseCase,
    private val updateFilmFavoriteStatusUseCase: UpdateFilmFavoriteStatusUseCase
) : BaseViewModel<FilmDetailsIntent, FilmDetailsState, FilmDetailsEffect>(initialState = FilmDetailsState()) {

    private val genres = MutableStateFlow<List<Genre>?>(null)
    private val film = MutableStateFlow<Film?>(null)

    init {
        observeDetails()
        loadGenres()
    }

    override fun handleIntent(intent: FilmDetailsIntent) {
        when (intent) {
            is FilmDetailsIntent.GetFilmDetails -> getFilmDetails(intent.id)
            FilmDetailsIntent.ToggleFavoritesStatus -> toggleFavoritesStatus()
            FilmDetailsIntent.BackClicked -> onBackClicked()
        }
    }

    private fun getFilmDetails(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val film = getFilmByIdUseCase(id)
            film?.let {
                withContext(Dispatchers.Main.immediate) {
                    this@FilmDetailsViewModel.film.update { film }
                }
            }
        }
    }

    private fun loadGenres() {
        getGenresUseCase()
            .flowOn(Dispatchers.IO)
            .onEach { resource ->
                when (resource) {
                    Resource.Loading -> Unit
                    is Resource.Error -> {
                        genres.update { resource.error.data }
                    }
                    is Resource.Success -> {
                        genres.update { resource.data }
                    }
                }
            }
            .flowOn(Dispatchers.Main)
            .launchIn(viewModelScope)
    }

    private fun observeDetails() {
        combine(
            genres.filterNotNull(),
            film.filterNotNull()
        ) { genres, film ->
            updateState { state -> state.copy(film = film.toDetails(genres))}
        }.launchIn(viewModelScope)
    }

    private fun toggleFavoritesStatus() {
        currentState.film?.let { film ->
            updateState { state -> state.copy(film = film.copy(isFavorite = film.isFavorite.not())) }
            viewModelScope.launch(Dispatchers.IO) {
                updateFilmFavoriteStatusUseCase(film.id, film.isFavorite.not())
            }
        }
    }

    private fun onBackClicked() {
        viewModelScope.launch {
            sendEffect(FilmDetailsEffect.NavigateBack)
        }
    }
}