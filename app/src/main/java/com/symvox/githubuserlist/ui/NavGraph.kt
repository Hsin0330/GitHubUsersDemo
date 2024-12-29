package com.symvox.githubuserlist.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
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
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Search,
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
//        navigation<Graph.UserGraph>(startDestination = Destinations.SearchUser) {
//            composable<Destinations.SearchUser> {
//                SearchScreen { user ->
//                    navController.navigate(Destinations.UserRepositoryList(user))
//                }
//            }
//            composable<Destinations.UserRepositoryList>(
//                typeMap = mapOf(typeOf<User>() to UserType),
//            ) { navBackStackEntry ->
//                UserRepoScreen()
//            }
//        }

//        navigation<Graph.RepoGraph>(
//            typeMap = mapOf(typeOf<User>() to UserType),
//            startDestination = Destinations.UserRepositoryList()
//        ) {
//            composable<Destinations.UserRepositoryList>(
//                typeMap = mapOf(typeOf<User>() to UserType),
//            ) { navBackStackEntry ->
//                UserRepoScreen()
//            }
//        }
    }
}