package com.raka.repolistkoin.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.raka.repolistkoin.data.model.local.RepoListLocal
import com.raka.repolistkoin.util.AppConfig

@Database(entities = [RepoListLocal::class], version = AppConfig.DB_VERSION)
abstract class AppDatabase : RoomDatabase() {
    abstract fun parametersDao(): ParametersDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, AppConfig.DB_NAME
                    ).build()
                }
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}