package shov.studio.ui.anime.ui.details.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import shov.studio.ui.anime.R
import shov.studio.ui.anime.data.models.RecommendationModel
import core.ui.common.modifiers.placeholder

@Composable
internal fun DetailsRecommendationsView(
    recommendations: ImmutableList<RecommendationModel>,
    onDetailsClick: (id: Int) -> Unit,
) {
    Column {
        Text(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            text = stringResource(R.string.recommendations),
            style = MaterialTheme.typography.titleLarge
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(recommendations) { response ->
                Column(
                    modifier = Modifier
                        .width(96.dp)
                        .clip(MaterialTheme.shapes.small)
                        .clickable { onDetailsClick(response.id) }
                        .padding(all = 4.dp)
                ) {
                    val painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(response.picture).size(Size.ORIGINAL)
                            .build()
                    )

                    Image(
                        modifier = Modifier
                            .height(128.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .align(Alignment.CenterHorizontally)
                            .placeholder(enabled = painter.state !is AsyncImagePainter.State.Success),
                        painter = painter,
                        contentDescription = response.title,
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 4.dp),
                        text = response.title,
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun DetailsRecommendationsPreview() {
    Surface {
        DetailsRecommendationsView(
            recommendations = persistentListOf(
                RecommendationModel(1, "Title", ""),
                RecommendationModel(1, "Title", ""),
                RecommendationModel(1, "Title", "")
            ), onDetailsClick = {})
    }
}