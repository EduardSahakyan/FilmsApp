package com.example.filmsapp.domain.usecases.film

import com.example.filmsapp.domain.models.film.Film
import com.example.filmsapp.domain.repositories.FilmsRepository
import com.example.filmsapp.domain.resource.Resource
import kotlinx.coroutines.flow.Flow

class GetFilmsUseCase(
    private val filmsRepository: FilmsRepository
) {

    operator fun invoke(page: Int, selectedGenre: Int? = null): Flow<Resource<List<Film>>> {
        return if (selectedGenre == null) {
            filmsRepository.getPopularFilms(page)
        } else {
            filmsRepository.getFilteredFilms(selectedGenre, page)
        }
    }

}