package com.example.qr_generator_example

import android.app.Application
import com.example.qr_generator_example.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class QRApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            // declare used Android context
            androidContext(this@QRApplication)
            // declare modules
            modules(appModule)
        }
    }
}
