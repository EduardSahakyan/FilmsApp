package com.example.filmsapp.data.repositories

import com.example.filmsapp.domain.resource.Resource
import com.example.filmsapp.domain.resource.RootError
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response

fun<RESPONSE, DOMAIN, ENTITY> safeApiCallWithCaching(
    apiCall: suspend () -> Response<RESPONSE>,
    dtoMapper: (RESPONSE) -> DOMAIN,
    entityMapper: (ENTITY) -> DOMAIN,
    saveToCache: suspend (RESPONSE?) -> Unit,
    loadFromCache: suspend () -> ENTITY,
    alwaysLoadFromCache: Boolean = false
) = flow {
    emit(Resource.Loading)
    val response = apiCall()
    if (response.isSuccessful) {
        response.body()?.let { data ->
            saveToCache(data)
            val result = if (alwaysLoadFromCache) {
                entityMapper(loadFromCache())
            } else {
                dtoMapper(data)
            }
            emit(Resource.Success(result))
        }
    } else {
        val cached = loadFromCache()?.let { entityMapper(it) }
        emit(Resource.Error(RootError.NetworkError(cached)))
    }
}.catch { throwable ->
    val cached = loadFromCache()?.let { entityMapper(it) }
    emit(Resource.Error(RootError.Error(throwable, cached)))
}