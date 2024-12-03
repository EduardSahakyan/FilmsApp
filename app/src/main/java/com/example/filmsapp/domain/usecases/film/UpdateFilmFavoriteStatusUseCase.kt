package com.example.filmsapp.domain.usecases.film

import com.example.filmsapp.domain.repositories.FilmsRepository

class UpdateFilmFavoriteStatusUseCase(
    private val filmsRepository: FilmsRepository
) {

    suspend operator fun invoke(filmId: Int, isFavorite: Boolean) {
        filmsRepository.updateFavoriteStatus(filmId, isFavorite)
    }

}