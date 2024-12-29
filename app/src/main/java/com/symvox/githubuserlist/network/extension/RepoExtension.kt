package com.symvox.githubuserlist.network.extension

import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.toColorInt
import com.apollographql.apollo.api.ApolloResponse
import com.symvox.githubuserlist.UserRepositoriesQuery
import com.symvox.githubuserlist.entity.Language
import com.symvox.githubuserlist.entity.Repo
import com.symvox.githubuserlist.ui.theme.Black

fun ApolloResponse<UserRepositoriesQuery.Data>.mapToUserRepositoryList(): List<Repo> {
    val repoList = arrayListOf<Repo>().also { repoList ->
        if (this.data?.user?.repositories?.nodes != null) {
            for (data in this.data!!.user?.repositories?.nodes!!) {
                if (data != null) {
                    var language: Language? = null
                    if (data.primaryLanguage != null) {
                        language = Language(
                            data.primaryLanguage.name,
                            data.primaryLanguage.color?.toColorInt() ?: Black.toArgb()
                        )
                    }

                    val repo = Repo(
                        data.name,
                        language,
                        data.description ?: "",
                        data.stargazerCount,
                        data.url.toString()
                    )
                    repoList.add(repo)
                }
            }
        }
    }
    return repoList
}