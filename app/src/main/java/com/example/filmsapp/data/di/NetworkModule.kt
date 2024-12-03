package com.example.filmsapp.data.di

import com.example.filmsapp.BuildConfig
import com.example.filmsapp.data.network.ApiService
import com.example.filmsapp.data.network.BASE_URL
import com.example.filmsapp.data.network.HEADER_AUTH
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

val networkModule = module {

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addNetworkInterceptor { chain ->
                val originalRequest = chain.request()
                val apiKey = BuildConfig.API_KEY
                val request = originalRequest.newBuilder()
                    .addHeader(HEADER_AUTH, "Bearer $apiKey")
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    single<ApiService> {
        Retrofit.Builder()
            .client(get())
            .baseUrl(BASE_URL)
            .build()
            .create()
    }

}