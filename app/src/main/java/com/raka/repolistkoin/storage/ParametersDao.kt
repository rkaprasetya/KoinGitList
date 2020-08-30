package com.raka.repolistkoin.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raka.repolistkoin.data.model.local.RepoListLocal
import io.reactivex.Flowable
@Dao
interface ParametersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertRepoList(data: List<RepoListLocal?>?)

    @Query("SELECT * from repolist")
    fun getRepoList(): List<RepoListLocal>

    @Query("DELETE FROM repolist")
    fun deleteRepoList()
}