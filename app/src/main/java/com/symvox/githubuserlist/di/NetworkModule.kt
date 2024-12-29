package com.symvox.githubuserlist.di

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    fun provideBaseUrl(): String = "https://api.github.com/graphql"

    @Provides
    @Singleton
    fun provideApollo(baseUrl: String): ApolloClient =
        ApolloClient.Builder()
            .serverUrl(baseUrl)
            .addHttpHeader("Authorization", "")
            .build()
}