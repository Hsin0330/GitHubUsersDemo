package com.symvox.githubuserlist.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo.ApolloClient
import com.symvox.githubuserlist.UserPreviewQuery
import com.symvox.githubuserlist.datasource.UserDataSource
import com.symvox.githubuserlist.entity.User
import com.symvox.githubuserlist.network.extension.mapToUserPreviewListFlow
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val apolloClient: ApolloClient,

    ) {
    fun searchUsers(query: String): Flow<PagingData<User>> {
        val config = PagingConfig(pageSize = 30, prefetchDistance = 10, enablePlaceholders = false)
        return Pager(config) {
            UserDataSource(apolloClient = apolloClient, query = query)
        }.flow
    }

    fun searchPreviewUsers(query: String): Flow<List<User>> {
        return apolloClient.query(UserPreviewQuery(query)).toFlow()
            .mapToUserPreviewListFlow()
    }
}