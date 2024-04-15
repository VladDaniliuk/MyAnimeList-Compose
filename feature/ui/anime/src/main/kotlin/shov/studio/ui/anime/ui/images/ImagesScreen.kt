package shov.studio.ui.anime.ui.images

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.size.Size
import kotlinx.collections.immutable.persistentListOf
import core.ui.common.pager.HorizontalPagerIndicator

@Composable
internal fun ImagesScreen(state: ImagesState) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        HorizontalPager(state = state.pagerState, pageSpacing = 32.dp) { index ->
            SubcomposeAsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current).data(state.uiState.images[index])
                    .size(Size.ORIGINAL).build(),
                contentDescription = null,
                loading = {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                },
            )
        }

        HorizontalPagerIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 16.dp),
            pagerState = state.pagerState,
            pageCount = state.uiState.images.size,
            activeColor = Color.White,
            inactiveColor = Color.Gray
        )

        IconButton(
            modifier = Modifier
                .statusBarsPadding()
                .padding(top = 12.dp, start = 8.dp),
            onClick = state.onBackClick
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = Icons.AutoMirrored.Rounded.ArrowBack.name,
                tint = Color.White
            )
        }
    }
}

@Preview
@Composable
fun ImagesPreview() {
    ImagesScreen(
        state = ImagesState(ImagesUiState(
            persistentListOf("https://api-cdn.myanimelist.net/images/anime/1576/110060.jpg"),
            status = ImagesUiState.LoadStatus.COMPLETE
        ), rememberPagerState(0) { 1 }) {}
    )
}
