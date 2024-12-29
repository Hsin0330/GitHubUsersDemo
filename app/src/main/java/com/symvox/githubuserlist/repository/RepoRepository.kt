package com.symvox.githubuserlist.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo.ApolloClient
import com.symvox.githubuserlist.datasource.RepoDataSource
import com.symvox.githubuserlist.entity.Repo
import kotlinx.coroutines.flow.Flow

class RepoRepository (
    private val apolloClient: ApolloClient
) {
    fun getRepositories(userLoginId: String): Flow<PagingData<Repo>> {
        val config = PagingConfig(pageSize = 30, prefetchDistance = 10, enablePlaceholders = false)
        return Pager(config) {
            RepoDataSource(apolloClient = apolloClient, userLoginId = userLoginId)
        }.flow
    }
}