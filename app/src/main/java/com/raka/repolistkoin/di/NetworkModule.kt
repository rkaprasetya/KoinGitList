package com.raka.repolistkoin.di

import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import com.raka.repolistkoin.data.network.ApiService
import com.raka.repolistkoin.util.AppConfig
import okhttp3.OkHttpClient
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(AppConfig.BASE_URL_GIT)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
    single {
        OkHttpClient.Builder()
            .addInterceptor(OkHttpProfilerInterceptor())
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }
    single {
        get<Retrofit>().create(ApiService::class.java)
    }
}