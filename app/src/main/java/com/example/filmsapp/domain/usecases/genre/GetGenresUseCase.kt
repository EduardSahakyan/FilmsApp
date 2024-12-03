package com.example.filmsapp.domain.usecases.genre

import com.example.filmsapp.domain.models.genre.Genre
import com.example.filmsapp.domain.repositories.FilmsRepository
import com.example.filmsapp.domain.resource.Resource
import kotlinx.coroutines.flow.Flow

class GetGenresUseCase(
    private val filmsRepository: FilmsRepository
) {

    operator fun invoke(): Flow<Resource<List<Genre>>> {
        return filmsRepository.getGenres()
    }

}