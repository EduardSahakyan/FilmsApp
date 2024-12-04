package com.example.filmsapp.domain.usecases.film

import com.example.filmsapp.domain.models.film.Film
import com.example.filmsapp.domain.repositories.FilmsRepository

class GetFilmByIdUseCase(
    private val filmsRepository: FilmsRepository
) {

    suspend operator fun invoke(id: Int): Film? {
        return filmsRepository.getFilmById(id)
    }

}