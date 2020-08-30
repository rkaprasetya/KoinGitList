package com.raka.repolistkoin.data.model.compact

import com.raka.repolistkoin.base.BaseViewModel

data class RepoResponseCompact(
    val state: BaseViewModel.State,
    val data: List<RepoCompact>?
)