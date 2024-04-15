package shov.studio.ui.anime.ui.main

import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import shov.studio.enums.MangaRankingType
import shov.studio.enums.RankingType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainScrollableTabRow(
    pagerState: PagerState,
    tabs: ImmutableList<RankingType>,
    modifier: Modifier = Modifier,
    onTabClick: (Int) -> Unit,
) {
    PrimaryScrollableTabRow(
        modifier = modifier,
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            SecondaryIndicator(
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
        tabs.forEachIndexed { index, type ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = { onTabClick(index) },
                text = { Text(text = stringResource(type.id)) }
            )
        }
    }
}

@Preview
@Composable
private fun MainScrollableTabPreview() {
    MainScrollableTabRow(
        pagerState = rememberPagerState { 0 },
        onTabClick = {},
        tabs = MangaRankingType.entries.toImmutableList()
    )
}
