package com.example.filmsapp.domain.resource

sealed interface Resource<out T> {

    data object Loading : Resource<Nothing>
    data class Success<T>(val data: T) : Resource<T>
    data class Error<T>(val error: RootError<T>) : Resource<T>

}

sealed class RootError<T>(open val data: T?) {
    data class Error<T>(val throwable: Throwable, override val data: T? = null) : RootError<T>(data)
    data class NetworkError<T>(override val data: T? = null): RootError<T>(data)
}