package com.raka.repolistkoin.di

import com.raka.repolistkoin.data.RepoListRepositoryImpl
import com.raka.repolistkoin.domain.GetRepoListUseCase
import com.raka.repolistkoin.domain.RepoListRepository
import com.raka.repolistkoin.presentation.RepoListViewModel
import org.koin.dsl.module.module

val applicationModule = module(override = true){
    factory <RepoListRepository>{ RepoListRepositoryImpl() }
    factory { GetRepoListUseCase() }
    factory { RepoListViewModel() }
}