package shov.studio.ui.anime.ui.details.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.size.Size
import shov.studio.enums.RelationType
import shov.studio.enums.Season
import shov.studio.ui.anime.R
import shov.studio.ui.anime.data.models.DetailsRelatedModel
import core.ui.common.modifiers.placeholder

@Composable
internal fun RelatedAnimeItem(
    relatedModel: DetailsRelatedModel,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current).data(relatedModel.picture)
                .size(Size.ORIGINAL).build()
        )

        Image(
            modifier = Modifier
                .size(width = 64.dp, height = 88.dp)
                .clip(MaterialTheme.shapes.medium)
                .placeholder(enabled = painter.state !is AsyncImagePainter.State.Success),
            painter = painter,
            contentDescription = relatedModel.title,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            Text(
                text = relatedModel.title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                relatedModel.startYear?.let { year ->
                    Text(text = year.toString(), style = MaterialTheme.typography.bodyMedium)

                    Box(
                        modifier = modifier
                            .padding(horizontal = 4.dp)
                            .size(4.dp)
                            .clip(MaterialTheme.shapes.extraSmall)
                            .background(LocalContentColor.current)
                    )
                }

                relatedModel.rating?.let { mean ->
                    Text(
                        text = stringResource(R.string.rating, mean),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Box(
                        modifier = modifier
                            .padding(horizontal = 4.dp)
                            .size(4.dp)
                            .clip(MaterialTheme.shapes.extraSmall)
                            .background(LocalContentColor.current)
                    )
                }

                Text(
                    text = stringResource(relatedModel.relationType.id),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview
@Composable
private fun RelatedAnimeItemPreview() {
    Surface {
        RelatedAnimeItem(
            relatedModel = DetailsRelatedModel(
                id = 0,
                title = "Title",
                picture = "https://api-cdn.myanimelist.net/images/anime/1576/110060.jpg",
                startYear = 2012,
                startSeason = Season.winter,
                rating = 9.8f,
                relationType = RelationType.alternative_setting
            )
        )
    }
}
