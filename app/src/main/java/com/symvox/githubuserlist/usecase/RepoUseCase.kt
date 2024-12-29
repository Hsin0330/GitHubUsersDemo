package com.symvox.githubuserlist.usecase

import androidx.paging.PagingData
import com.symvox.githubuserlist.entity.Repo
import com.symvox.githubuserlist.repository.RepoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class RepoUseCase(
    private val repoRepository: RepoRepository
) {

    fun getRepositories(userId: String): Flow<PagingData<Repo>> {
        return repoRepository.getRepositories(userId)
            .flowOn(Dispatchers.IO)
    }
}