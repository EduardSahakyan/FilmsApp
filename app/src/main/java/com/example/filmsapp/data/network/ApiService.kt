package com.example.filmsapp.data.network

import com.example.filmsapp.data.network.dtos.film.FilmsDto
import com.example.filmsapp.data.network.dtos.genre.GenresDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(ENDPOINT_POPULAR_FILMS)
    suspend fun getPopularFilms(@Query(QUERY_PAGE) page: Int): Response<FilmsDto>

    @GET(ENDPOINT_FILTER_FILMS)
    suspend fun getFilteredFilms(@Query(QUERY_GENRE) genreId: Int, @Query(QUERY_PAGE) page: Int): Response<FilmsDto>

    @GET(ENDPOINT_SEARCH_FILMS)
    suspend fun searchFilms(@Query(QUERY_SEARCH) query: String): Response<FilmsDto>

    @GET(ENDPOINT_GET_GENRES)
    suspend fun getGenres(): Response<GenresDto>

}