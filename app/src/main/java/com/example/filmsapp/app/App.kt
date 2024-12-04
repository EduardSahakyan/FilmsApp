package com.example.filmsapp.app

import android.app.Application
import com.example.filmsapp.data.di.dataModule
import com.example.filmsapp.domain.di.domainModule
import com.example.filmsapp.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@App)
            modules(dataModule, domainModule, presentationModule)
        }
    }

}