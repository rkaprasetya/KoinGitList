package com.raka.repolistkoin.di

import androidx.room.Room
import com.raka.repolistkoin.storage.AppDatabase
import com.raka.repolistkoin.util.AppConfig.DB_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val databaseModule = module {
    single {
        synchronized(AppDatabase::class) {
            Room.databaseBuilder(
                androidContext().applicationContext,
                AppDatabase::class.java, DB_NAME
            ).build()
        }.parametersDao()
    }
}