package com.symvox.githubuserlist.ui.repo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.symvox.githubuserlist.R
import com.symvox.githubuserlist.entity.Language
import com.symvox.githubuserlist.entity.Repo
import com.symvox.githubuserlist.ui.search.UserItem

@Composable
fun UserRepoScreen(
    modifier: Modifier = Modifier,
    viewModel: UserRepoViewModel = hiltViewModel()
) {
    val user by viewModel.user.collectAsState()
    val repoList by rememberUpdatedState(newValue = viewModel.userRepoList.collectAsLazyPagingItems())
    val uriHandler = LocalUriHandler.current

    Column(modifier = modifier) {
        UserItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), user = user
        )
        LazyColumn {
            items(repoList.itemCount) { index ->
                val repo = repoList[index]
                if (repo != null) {
                    RepoItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        repo = repo
                    ) { uri ->
                        uriHandler.openUri(uri)
                    }
                }
            }
        }
    }
}

@Composable
fun RepoItem(
    modifier: Modifier = Modifier,
    repo: Repo,
    onClick: ((String) -> Unit)? = null
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        border = BorderStroke(1.dp, Color.LightGray),
        modifier = modifier.clickable {
            onClick?.invoke(repo.htmlUrl)
        }
    ) {
        ConstraintLayout(modifier = modifier.padding(16.dp)) {
            val (nameText, descriptionText, languageText, stargazersCountText) = createRefs()

            Text(
                text = repo.name,
                modifier = Modifier.constrainAs(nameText) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(descriptionText.top)
                },
                fontWeight = FontWeight.W600
            )

            if (repo.description.isNotEmpty()) {
                Text(
                    text = repo.description,
                    modifier = Modifier.constrainAs(descriptionText) {
                        start.linkTo(parent.start)
                        top.linkTo(nameText.bottom, 16.dp)
                    }
                )
            }

            if (repo.language != null) {
                Text(
                    text = repo.language.name,
                    modifier = Modifier.constrainAs(languageText) {
                        start.linkTo(parent.start)
                        top.linkTo(descriptionText.bottom, 16.dp)
                    },
                    fontWeight = FontWeight.W500,
                    color = Color(repo.language.color)
                )
            }

            Row(
                Modifier
                    .constrainAs(stargazersCountText) {
                        start.linkTo(languageText.end, if (repo.language != null) 16.dp else 0.dp)
                        top.linkTo(descriptionText.bottom, 16.dp)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painterResource(R.drawable.baseline_star_border_24), contentDescription = "")
                Text(text = "${repo.stargazersCount}")
            }
        }
    }
}

@Preview
@Composable
fun PreViewUserItem() {
    RepoItem(
        repo = Repo(
            name = "Wind's Repo",
            language = Language(name = "Kotlin", color = Color.Red.toArgb()),
            description = "123456",
            stargazersCount = 10,
            htmlUrl = "https://github.com"
        )
    )
}