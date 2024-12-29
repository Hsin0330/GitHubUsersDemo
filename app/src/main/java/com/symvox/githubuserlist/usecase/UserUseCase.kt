package com.symvox.githubuserlist.usecase

import androidx.paging.PagingData
import com.symvox.githubuserlist.entity.User
import com.symvox.githubuserlist.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class UserUseCase(
    private val userRepository: UserRepository
) {

    fun searchUsers(query: String): Flow<PagingData<User>> {
        return userRepository.searchUsers(query)
            .flowOn(Dispatchers.IO)
    }

    fun searchPreviewUser(query: String): Flow<List<User>> {
        return userRepository.searchPreviewUsers(query)
            .flowOn(Dispatchers.IO)
    }
}