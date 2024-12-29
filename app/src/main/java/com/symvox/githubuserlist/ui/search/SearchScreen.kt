@file:OptIn(ExperimentalMaterial3Api::class)

package com.symvox.githubuserlist.ui.search

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.symvox.githubuserlist.R
import com.symvox.githubuserlist.entity.User

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    navigateToUserRepoScreen: ((User) -> Unit)? = null
) {

    val searchText by viewModel.searchTextPreview.collectAsStateWithLifecycle()
    val isSearching by viewModel.isSearching.collectAsStateWithLifecycle()
    val userPreviewList by viewModel.userPreviewList.collectAsStateWithLifecycle()
    val userList by rememberUpdatedState(newValue = viewModel.userList.collectAsLazyPagingItems())

    val searchBarPadding by animateDpAsState(
        targetValue = if (isSearching) 0.dp else 16.dp,
        label = "Search bar padding"
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            TopSearchBar(
                Modifier
                    .fillMaxWidth()
                    .padding(searchBarPadding),
                searchText,
                viewModel::onSearchTextChange,
                viewModel::onSearchTextChange,
                isSearching, {
                    viewModel.onToggleSearch()
                }, {
                    viewModel.onToggleSearch()
                }) {
                LazyColumn {
                    items(userPreviewList) { user ->
                        SearchSuggestionUserItem(modifier = Modifier.padding(16.dp), user = user) {
                            viewModel.onToggleSearch()
                            viewModel.onSearchTextChange(user.name)
                            viewModel.onSearch(user.name)
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(userList.itemCount) { index ->
                val user = userList[index]
                if (user != null) {
                    UserItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp), user = user
                    ) {
                        navigateToUserRepoScreen?.invoke(it)
                    }
                }
            }
        }
    }
}

@Composable
fun TopSearchBar(
    modifier: Modifier = Modifier,
    searchText: String,
    onQueryChange: (String) -> Unit,
    onSearchChange: (String) -> Unit,
    isSearching: Boolean,
    onToggleSearch: (Boolean) -> Unit,
    onExpandedChange: (Boolean) -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = searchText,
                onQueryChange = onQueryChange,
                onSearch = onSearchChange,
                expanded = isSearching,
                onExpandedChange = onToggleSearch,
                enabled = true
            )
        },
        expanded = isSearching,
        onExpandedChange = onExpandedChange,
        modifier = modifier
    ) {
        content()
    }
}

@Composable
fun SearchSuggestionUserItem(
    modifier: Modifier = Modifier,
    user: User,
    onClick: ((String) -> Unit)? = null
) {
    Text(
        text = user.name,
        modifier = modifier
            .clickable {
                onClick?.invoke(user.name)
            }
    )
}

@Composable
fun UserItem(
    modifier: Modifier = Modifier,
    user: User,
    onClick: ((User) -> Unit)? = null
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        border = BorderStroke(1.dp, Color.Black),
        modifier = modifier
            .clickable {
                onClick?.invoke(user)
            }
    ) {
        ConstraintLayout(
            modifier = modifier.padding(16.dp)
        ) {
            val (avatarImage, fullNameText, loginIdText, followersText, followingsText) = createRefs()

            AsyncImage(
                model = user.avatarUrl,
                contentDescription = null,
                modifier = Modifier
                    .width(56.dp)
                    .height(56.dp)
                    .clip(CircleShape)
                    .constrainAs(avatarImage) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)

                    }
            )

            Text(
                text = user.fullName,
                modifier = Modifier.constrainAs(fullNameText) {
                    start.linkTo(avatarImage.end, 16.dp)
                    top.linkTo(avatarImage.top)
                },
                fontWeight = FontWeight.W600
            )

            Text(
                text = user.name,
                modifier = Modifier.constrainAs(loginIdText) {
                    start.linkTo(fullNameText.end, 8.dp)
                    baseline.linkTo(fullNameText.baseline)
                },
                fontWeight = FontWeight.W300
            )

            Text(
                text = stringResource(R.string.followers_count, user.followers ?: 0),
                modifier = Modifier.constrainAs(followersText) {
                    start.linkTo(avatarImage.end, 16.dp)
                    top.linkTo(fullNameText.bottom, 16.dp)
                }
            )

            Text(
                text = stringResource(R.string.followings_count, user.following ?: 0),
                modifier = Modifier.constrainAs(followingsText) {
                    start.linkTo(followersText.end, 8.dp)
                    top.linkTo(fullNameText.bottom, 16.dp)
                }
            )
        }
    }
}

@Preview
@Composable
fun PreViewUserItem() {
    UserItem(
        user = User(
            id = "123",
            name = "Wind",
            fullName = "Wind 123",
            avatarUrl = null,
            followers = 10,
            following = 20
        )
    )
}