package com.symvox.githubuserlist.ui.repo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.cachedIn
import com.symvox.githubuserlist.entity.User
import com.symvox.githubuserlist.usecase.RepoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class UserRepoViewModel @Inject constructor(
    private val repoUseCase: RepoUseCase,
    state: SavedStateHandle
) : ViewModel() {

    private val _user = MutableStateFlow(state.toRoute<User>())
    val user = _user.asStateFlow()

    val userRepoList = user
        .flatMapLatest {
            repoUseCase.getRepositories(it.name).cachedIn(viewModelScope)
        }.catch {
            it.printStackTrace()
        }
}