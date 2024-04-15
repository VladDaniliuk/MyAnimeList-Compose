package shov.studio.ui.anime.ui.userlist.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import shov.studio.trackanimelist.navigator.AnimeRoute
import shov.studio.ui.anime.R

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
internal fun UserListScrollableTabRow(pagerState: PagerState, type: String) {
    val coroutineScope = rememberCoroutineScope()

    PrimaryScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                    .clip(
                        MaterialTheme.shapes.medium.copy(
                            bottomStart = CornerSize(0),
                            bottomEnd = CornerSize(0)
                        )
                    )
            )
        }
    ) {
        listOf(
            stringResource(
                if (type == AnimeRoute.Type.anime) R.string.watching else R.string.reading
            ),
            stringResource(R.string.completed),
            stringResource(R.string.on_hold),
            stringResource(R.string.dropped),
            stringResource(
                if (type == AnimeRoute.Type.anime) R.string.plan_to_watch else R.string.plan_to_read
            )
        ).forEachIndexed { index, type ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch { pagerState.animateScrollToPage(index) }
                },
                text = { Text(text = type) }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun UserListScrollableTabRowPreview() {
    UserListScrollableTabRow(pagerState = rememberPagerState { 5 }, type = AnimeRoute.Type.anime)
}
