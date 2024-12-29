package com.symvox.githubuserlist.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.symvox.githubuserlist.UserQuery
import com.symvox.githubuserlist.entity.User
import com.symvox.githubuserlist.network.extension.mapToUserList

class UserDataSource(
    private val apolloClient: ApolloClient,
    private val query: String
) : PagingSource<String, User>() {
    override fun getRefreshKey(state: PagingState<String, User>): String? {
        return null
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, User> {
        return try {
            val response = apolloClient.query(UserQuery(query, Optional.present(params.key), Optional.present(params.loadSize))).execute()
            val userList = response.mapToUserList()
            LoadResult.Page(
                data = userList,
                prevKey = null,
                nextKey = response.data?.search?.pageInfo?.endCursor
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }
}