package com.symvox.githubuserlist.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.symvox.githubuserlist.UserRepositoriesQuery
import com.symvox.githubuserlist.entity.Repo
import com.symvox.githubuserlist.network.extension.mapToUserRepositoryList

class RepoDataSource(
    private val apolloClient: ApolloClient,
    private val userLoginId: String
) : PagingSource<String, Repo>() {
    override fun getRefreshKey(state: PagingState<String, Repo>): String? {
        return null
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Repo> {
        return try {
            val response = apolloClient.query(UserRepositoriesQuery(userLoginId, Optional.present(params.key), Optional.present(params.loadSize))).execute()
            val repoList = response.mapToUserRepositoryList()
            LoadResult.Page(
                data = repoList,
                prevKey = null,
                nextKey = response.data?.user?.repositories?.pageInfo?.endCursor
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}