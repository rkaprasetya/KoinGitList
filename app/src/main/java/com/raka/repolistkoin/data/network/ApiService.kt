package com.raka.repolistkoin.data.network

import com.raka.repolistkoin.data.model.response.GitResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search/repositories")
    fun getRepoRx(@Query("q") search: String = "trending", @Query("sort") sort: String = "stars"): Single<GitResponse>
}