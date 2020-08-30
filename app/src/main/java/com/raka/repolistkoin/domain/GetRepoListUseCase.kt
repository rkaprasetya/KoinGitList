package com.raka.repolistkoin.domain

import com.raka.repolistkoin.data.model.compact.RepoCompact
import com.raka.repolistkoin.data.model.local.RepoListLocal
import com.raka.repolistkoin.presentation.RepoListMapper
import io.reactivex.Single
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject


class GetRepoListUseCase():KoinComponent {
    private val repoListRepository : RepoListRepository by inject()
    val mapper = RepoListMapper()
    fun getRepoRemoteData(): Single<List<RepoCompact>> = repoListRepository.getRepoList().map { mapper.convertRemoteToRepoCompactData(it.items) }
    suspend fun insertToLocal(data:List<RepoListLocal>){
        repoListRepository.insertRepoListToDb(data)
    }
    suspend fun loadLocalData() = repoListRepository.getRepoListLocal().map { mapper.mapLocalToRepoCompact(it) }
}