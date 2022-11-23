package com.mbt925.shutterimagesearch

import android.app.Application
import com.mbt925.shutterimagesearch.di.appModule
import com.mbt925.shutterimagesearch.networking.di.networkingModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)

            modules(
                listOf(
                    appModule,
                    networkingModule,
                )
            )
        }
    }

    override fun onTerminate() {
        super.onTerminate()

        stopKoin()
    }

}
