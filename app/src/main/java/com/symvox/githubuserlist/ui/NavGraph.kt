package com.symvox.githubuserlist.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.symvox.githubuserlist.entity.User
import com.symvox.githubuserlist.ui.repo.UserRepoScreen
import com.symvox.githubuserlist.ui.search.SearchScreen
import kotlin.reflect.typeOf

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: Any = Search
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable<Search> {
            SearchScreen { user ->
                navController.navigate(user)
            }
        }
        composable<User>(
            typeMap = mapOf(typeOf<User>() to serializableType<User>()),
        ) { navBackStackEntry ->
            UserRepoScreen()
        }
    }
}