package com.symvox.githubuserlist.di

import com.apollographql.apollo.ApolloClient
import com.symvox.githubuserlist.repository.RepoRepository
import com.symvox.githubuserlist.repository.UserRepository
import com.symvox.githubuserlist.usecase.RepoUseCase
import com.symvox.githubuserlist.usecase.UserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserRepository(apolloClient: ApolloClient): UserRepository =
        UserRepository(apolloClient)

    @Provides
    @Singleton
    fun provideRepoRepository(apolloClient: ApolloClient): RepoRepository =
        RepoRepository(apolloClient)

    @Provides
    @Singleton
    fun provideUserUseCase(userRepository: UserRepository): UserUseCase =
        UserUseCase(userRepository)

    @Provides
    @Singleton
    fun provideRepoUseCase(repoRepository: RepoRepository): RepoUseCase =
        RepoUseCase(repoRepository)
}