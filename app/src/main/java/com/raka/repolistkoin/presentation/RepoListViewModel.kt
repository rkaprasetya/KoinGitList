package com.raka.repolistkoin.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.raka.repolistkoin.base.BaseViewModel
import com.raka.repolistkoin.data.model.compact.RepoCompact
import com.raka.repolistkoin.data.model.compact.RepoResponseCompact
import com.raka.repolistkoin.util.Utils
import com.raka.repolistkoin.domain.GetRepoListUseCase
import com.raka.repolistkoin.util.EventWrapper
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class RepoListViewModel:BaseViewModel(),KoinComponent {
    var repoCompactData = MutableLiveData<RepoResponseCompact>()
    var disposable: MutableLiveData<Disposable> = MutableLiveData()
    var searchKeyWord: MutableLiveData<String> = MutableLiveData()
    var isLoaded = MutableLiveData<Boolean>().apply { value = false }
    private var completeList: List<RepoCompact> = mutableListOf()
    private val mapper = RepoListMapper()
    private val repoListUseCase : GetRepoListUseCase by inject()

    fun loadRepoDataCA() {
        launch {
            val isInternetAvailable = withContext(Dispatchers.IO) {
                Utils.isInternetAvailable()
            }
            if (isInternetAvailable) {
                val disposable = repoListUseCase.getRepoRemoteData().subscribe({
                    repoCompactData.value =
                        RepoResponseCompact(State.SUCCESS, it)
                    _toastMessage.value = EventWrapper("Using Server Data")
                    onLoading.value = false
                    isLoaded.value = true
                    completeList = it
                }, {
                    repoCompactData.value = RepoResponseCompact(State.FAIL, null)
                    onLoading.value = false
                })
                compositeDisposable.add(disposable)
            } else {
                val data = repoListUseCase.loadLocalData()
                if (!data.isNullOrEmpty()) {
                    repoCompactData.value =
                        RepoResponseCompact(State.SUCCESS, data)
                    _toastMessage.value = EventWrapper("Using Local Data")
                    isLoaded.value = true
                    completeList = data
                }else{
                    repoCompactData.value = RepoResponseCompact(State.FAIL, null)
                }
                onLoading.value = false
            }
        }
    }

}