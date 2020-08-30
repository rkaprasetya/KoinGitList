package com.raka.repolistkoin

import android.app.Application
import com.raka.repolistkoin.di.applicationModule
import com.raka.repolistkoin.di.databaseModule
import com.raka.repolistkoin.di.networkModule
import org.koin.android.ext.android.startKoin

class BaseApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(applicationModule, networkModule, databaseModule))
    }
}
