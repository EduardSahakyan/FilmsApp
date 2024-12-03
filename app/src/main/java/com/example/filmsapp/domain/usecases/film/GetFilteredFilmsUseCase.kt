package com.example.filmsapp.domain.usecases.film

import com.example.filmsapp.domain.models.film.Film
import com.example.filmsapp.domain.repositories.FilmsRepository
import com.example.filmsapp.domain.resource.Resource
import kotlinx.coroutines.flow.Flow

class GetFilteredFilmsUseCase(
    private val filmsRepository: FilmsRepository
) {

    operator fun invoke(genreId: Int, page: Int): Flow<Resource<List<Film>>> {
        return filmsRepository.getFilteredFilms(genreId, page)
    }

}