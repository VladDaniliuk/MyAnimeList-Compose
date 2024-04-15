package shov.studio.ui.anime.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.size.Size
import shov.studio.trackanimelist.navigator.AnimeRoute
import shov.studio.ui.anime.R
import shov.studio.ui.anime.ui.details.ui.DetailsDatesView
import shov.studio.ui.anime.ui.details.ui.DetailsGenresView
import shov.studio.ui.anime.ui.details.ui.DetailsInfoView
import shov.studio.ui.anime.ui.details.ui.DetailsPicturesView
import shov.studio.ui.anime.ui.details.ui.DetailsRatingView
import shov.studio.ui.anime.ui.details.ui.DetailsRecommendationsView
import shov.studio.ui.anime.ui.details.ui.DetailsRelatedView
import shov.studio.ui.anime.ui.details.ui.DetailsStatisticView
import shov.studio.ui.anime.ui.details.ui.DetailsSynopsisView
import shov.studio.ui.anime.ui.details.ui.DetailsUserView
import core.ui.common.modifiers.placeholder
import core.ui.common.scaffold.SnackbarScaffold

@Composable
internal fun DetailsView(state: DetailsContract.State, event: (DetailsContract.Event) -> Unit) {
    SnackbarScaffold(hostState = state.snackbarHostState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .systemBarsPadding()
        ) {
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(state.details.picture).size(Size.ORIGINAL)
                    .build()
            )

            Image(
                modifier = Modifier
                    .size(width = 192.dp, height = 256.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .align(Alignment.CenterHorizontally)
                    .placeholder(enabled = painter.state !is AsyncImagePainter.State.Success)
                    .clickable(onClick = { event(DetailsContract.Event.OnIconClick(0)) }),
                painter = painter,
                contentDescription = state.details.title,
                contentScale = ContentScale.Crop
            )

            DetailsInfoView(
                onNameClick = { event(DetailsContract.Event.OnNameClick) },
                detailsState = state
            )

            DetailsUserView(detailsState = state, event = event)

            DetailsGenresView(genres = state.details.genres, isLoading = state.isLoading)

            DetailsDatesView(
                startDate = state.details.startDate,
                endDate = state.details.endDate,
                isLoading = state.isLoading
            )

            DetailsSynopsisView(isLoading = state.isLoading, synopsis = state.details.synopsis)

            DetailsRatingView(details = state.details, isLoading = state.isLoading)

            DetailsStatisticView(status = state.details.statistic, isLoading = state.isLoading)

            DetailsPicturesView(
                pictures = state.details.pictures,
                isLoading = state.isLoading,
                onImageClick = { index -> event(DetailsContract.Event.OnIconClick(index)) }
            )

            if (state.details.relatedAnime.isNotEmpty()) DetailsRelatedView(
                text = stringResource(R.string.related_anime),
                list = state.details.relatedAnime,
                onRelatedClick = { event(DetailsContract.Event.OnRelatedClick(AnimeRoute.Type.anime)) },
                onRelatedItemClick = { id -> event(DetailsContract.Event.OnAnimeClick(id)) }
            )

            if (state.details.relatedManga.isNotEmpty()) DetailsRelatedView(
                text = stringResource(R.string.related_manga),
                list = state.details.relatedManga,
                onRelatedClick = { event(DetailsContract.Event.OnRelatedClick(AnimeRoute.Type.manga)) },
                onRelatedItemClick = { id -> event(DetailsContract.Event.OnMangaClick(id)) }
            )

            if (state.details.recommendations.isNotEmpty()) DetailsRecommendationsView(
                recommendations = state.details.recommendations,
                onDetailsClick = { id -> event(DetailsContract.Event.OnDetailsClick(id)) }
            )
        }
    }
}

@Preview
@Composable
private fun DetailsPreview(
) {
    DetailsView(state = DetailsContract.State(type = "anime", isAuthorized = true)) {}
}
