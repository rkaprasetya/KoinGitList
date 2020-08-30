package com.raka.repolistkoin.data

import android.annotation.SuppressLint
import android.util.Log
import com.raka.repolistkoin.data.model.compact.RepoCompact
import com.raka.repolistkoin.data.model.local.RepoListLocal
import com.raka.repolistkoin.data.model.response.GitResponse
import com.raka.repolistkoin.data.model.response.Item
import com.raka.repolistkoin.data.network.ApiClient
import com.raka.repolistkoin.presentation.RepoListMapper
import com.raka.repolistkoin.data.network.ApiService
import com.raka.repolistkoin.domain.RepoListRepository
import com.raka.repolistkoin.storage.AppDatabase
import com.raka.repolistkoin.storage.ParametersDao
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import kotlin.coroutines.CoroutineContext
class RepoListRepositoryImpl ():
    RepoListRepository,CoroutineScope,KoinComponent {
    private val service: ApiService by inject()
    private val dao: ParametersDao by inject()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job
    private var job = Job()
    val mapper = RepoListMapper()
    @SuppressLint("CheckResult")
    override fun getRepoList():Single<GitResponse>{
        val response =  service.getRepoRx().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        response.doAfterSuccess {
            insertRepoListToDb(mapper.convertRemoteToLocal(it.items)) }
        return response
    }

    override fun getRepoCompact(onResult: (isSuccess: Boolean, response: List<RepoCompact>?) -> Unit) {
        val disposable = service.getRepoRx().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({result ->
                onResult(true,setRepoCompactData(result.items))
            },{
                onResult(false,null)
            })
    }

    override fun getRepoRemote(onResult: (isSuccess: Boolean, response: List<Item>?) -> Unit) {
        val disposable = service.getRepoRx().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({result ->
                onResult(true,result.items)
            },{
                onResult(false,null)
            })
    }

    override fun insertRepoListToDb(data: List<RepoListLocal>) {
        launch {
            dao.deleteRepoList()
            dao.insertRepoList(data)
        }
    }

    override suspend fun getRepoListLocal(): List<RepoListLocal> {
        return withContext(Dispatchers.IO) {
            dao.getRepoList()
        }
    }

    private fun setRepoCompactData(item: List<Item>):List<RepoCompact> {
        val dataCompact: MutableList<RepoCompact> = mutableListOf()
        item.forEach { data ->
            RepoCompact(
                data.full_name,
                data.description,
                data.forks_count,
                data.stargazers_count,
                data.open_issues_count,
                data.owner.avatar_url,
                data.owner.html_url
            ).let { dataCompact.add(it) }
        }
        return dataCompact
    }
}