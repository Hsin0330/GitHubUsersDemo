package com.symvox.githubuserlist.network.extension

import com.apollographql.apollo.api.ApolloResponse
import com.symvox.githubuserlist.UserPreviewQuery
import com.symvox.githubuserlist.UserQuery
import com.symvox.githubuserlist.entity.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Flow<ApolloResponse<UserPreviewQuery.Data>>.mapToUserPreviewListFlow(): Flow<List<User>> {
    return map { userPreviewData ->
        val userList = arrayListOf<User>().also { userList ->
            if (userPreviewData.data?.search?.nodes != null) {
                for (data in userPreviewData.data!!.search.nodes!!) {
                    if (data?.onUser != null) {
                        val user = User(
                            data.onUser.id,
                            data.onUser.login,
                            data.onUser.name ?: "",
                        )
                        userList.add(user)
                    }
                }
            }
        }
        userList
    }
}

fun ApolloResponse<UserQuery.Data>.mapToUserList(): List<User> {
    val userList = arrayListOf<User>().also { userList ->
        if (this.data?.search?.nodes != null) {
            for (data in this.data!!.search.nodes!!) {
                if (data?.onUser != null) {
                    val user = User(
                        data.onUser.id,
                        data.onUser.login,
                        data.onUser.name ?: "",
                        data.onUser.avatarUrl.toString(),
                        data.onUser.followers.totalCount,
                        data.onUser.following.totalCount
                    )
                    userList.add(user)
                }
            }
        }
    }
    return userList
}