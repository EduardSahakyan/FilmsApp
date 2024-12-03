package com.example.filmsapp.data.network.dtos.film

import com.google.gson.annotations.SerializedName

class FilmResultDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    @SerializedName("title")
    val title: String,
    @SerializedName("overview")
    val description: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("vote_average")
    val voteAvg: Double
)