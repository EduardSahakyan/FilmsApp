package com.example.filmsapp.domain.resource

sealed interface Resource<out T> {

    data object Loading : Resource<Nothing>
    data class Success<T>(val data: T) : Resource<T>
    data class Error<T>(val error: RootError<T>) : Resource<T>

}

sealed interface RootError<T> {
    data class Error<T>(val throwable: Throwable, val data: T? = null) : RootError<T>
    data class NetworkError<T>(val data: T? = null): RootError<T>
}