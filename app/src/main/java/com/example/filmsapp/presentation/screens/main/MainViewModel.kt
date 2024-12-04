package com.example.filmsapp.presentation.screens.main

import androidx.lifecycle.viewModelScope
import com.example.filmsapp.domain.resource.Resource
import com.example.filmsapp.domain.usecases.film.GetFilmsUseCase
import com.example.filmsapp.domain.usecases.film.SearchFilmsUseCase
import com.example.filmsapp.domain.usecases.genre.GetGenresUseCase
import com.example.filmsapp.presentation.base.BaseViewModel
import com.example.filmsapp.presentation.screens.main.contract.MainEffect
import com.example.filmsapp.presentation.screens.main.contract.MainIntent
import com.example.filmsapp.presentation.screens.main.contract.MainState
import com.example.filmsapp.presentation.screens.main.mappers.toListItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val getFilmsUseCase: GetFilmsUseCase,
    private val searchFilmsUseCase: SearchFilmsUseCase,
    private val getGenresUseCase: GetGenresUseCase
) : BaseViewModel<MainIntent, MainState, MainEffect>(initialState = MainState()) {

    private var currentPage: Int = 0
    private val searchQuery = MutableStateFlow("")
    private var loadingJob: Job? = null

    init {
        observeSearchQuery()
        loadNextPage()
        loadGenres()
    }

    override fun handleIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.ChangeSearchQuery -> changeSearchQuery(intent.query)
            MainIntent.LoadNextPage -> loadNextPage()
            is MainIntent.SetGenre -> setGenre(intent.id)
            MainIntent.ToggleSearchMode -> toggleSearchMode()
            MainIntent.Refresh -> refresh()
            is MainIntent.FilmClicked -> onFilmCLicked(intent.id)
        }
    }

    private fun loadNextPage() {
        if (currentState.searchMode) return
        if (loadingJob?.isActive == true) return
        currentPage++
        val selectedGenre = currentState.selectedGenre
        loadingJob = getFilmsUseCase(currentPage, selectedGenre)
            .flowOn(Dispatchers.IO)
            .onEach { resource ->
                when(resource) {
                    Resource.Loading -> {
                        updateState { state -> state.copy(isLoading = true) }
                    }
                    is Resource.Error -> {
                        if (resource.error.data.isNullOrEmpty()) {
                            sendEffect(MainEffect.ShowConnectionErrorToast)
                            currentPage--
                        } else {
                            resource.error.data?.let {
                                val films = currentState.films.toMutableSet()
                                films.addAll(it.toListItems())
                                updateState { state -> state.copy(films = films.toList()) }
                            }
                        }
                    }
                    is Resource.Success -> {
                        if (resource.data.isEmpty()) {
                            currentPage--
                        } else {
                            val films = currentState.films.toMutableSet()
                            films.addAll(resource.data.toListItems())
                            updateState { state -> state.copy(films = films.toList()) }
                        }
                    }
                }
            }
            .onCompletion {
                delay(REFRESH_INDICATOR_DELAY)
                updateState { state -> state.copy(isLoading = false) }
            }
            .flowOn(Dispatchers.Main)
            .launchIn(viewModelScope)
    }

    private fun toggleSearchMode() {
        val searchMode = currentState.searchMode.not()
        updateState { state -> state.copy(searchMode = searchMode, searchQuery = "") }
        if (searchMode.not()) {
            refresh()
        }
    }

    private fun changeSearchQuery(query: String) {
        updateState { state -> state.copy(searchQuery = query) }
        searchQuery.update { query }
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            searchQuery
                .debounce(SEARCH_DEBOUNCE_DELAY)
                .filter { it.isNotBlank() }
                .collectLatest(::searchFilms)
        }
    }

    private fun searchFilms(query: String) {
        loadingJob?.cancel()
        loadingJob = searchFilmsUseCase(query)
            .flowOn(Dispatchers.IO)
            .onEach { resource ->
                when (resource) {
                    Resource.Loading -> {
                        updateState { state -> state.copy(isLoading = true) }
                    }
                    is Resource.Error -> {
                        resource.error.data?.let {
                            updateState { state -> state.copy(films = it.toListItems()) }
                        }
                    }
                    is Resource.Success -> {
                        updateState { state -> state.copy(films = resource.data.toListItems()) }
                    }
                }
            }
            .onCompletion {
                sendEffect(MainEffect.ScrollToTop)
                delay(REFRESH_INDICATOR_DELAY)
                updateState { state -> state.copy(isLoading = false) }
            }
            .flowOn(Dispatchers.Main)
            .launchIn(viewModelScope)
    }

    private fun setGenre(id: Int) {
        val selected = if (currentState.selectedGenre == id) null else id
        updateState { state -> state.copy(selectedGenre = selected) }
        refresh()
    }

    private fun refresh() {
        updateState { state -> state.copy(films = emptyList()) }
        if (currentState.searchMode) {
            searchFilms(currentState.searchQuery)
        } else {
            currentPage = 0
            loadNextPage()
        }
    }

    private fun onFilmCLicked(id: Int) {
        viewModelScope.launch {
            sendEffect(MainEffect.NavigateToDetails(id))
        }
    }

    private fun loadGenres() {
        getGenresUseCase()
            .flowOn(Dispatchers.IO)
            .onEach { resource ->
                when (resource) {
                    Resource.Loading -> Unit
                    is Resource.Error -> {
                        resource.error.data?.let {
                            updateState { state -> state.copy(genres = it) }
                        }
                    }
                    is Resource.Success -> {
                        updateState { state -> state.copy(genres = resource.data) }
                    }
                }
            }
            .flowOn(Dispatchers.Main)
            .launchIn(viewModelScope)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 500L
        private const val REFRESH_INDICATOR_DELAY = 500L
    }

}