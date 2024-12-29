package com.symvox.githubuserlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.symvox.githubuserlist.ui.NavGraph
import com.symvox.githubuserlist.ui.theme.GithubUserListTheme
import com.symvox.githubuserlist.ui.theme.White
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubUserListTheme {
                val navController = rememberNavController()
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .background(White)
                ) {
                    NavGraph(navController = navController)
                }
            }
        }
    }
}