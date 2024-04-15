package shov.studio.ui.anime.ui.details.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.size.Size
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import shov.studio.ui.anime.R
import core.ui.common.modifiers.placeholder

@Composable
internal fun DetailsPicturesView(
    isLoading: Boolean, pictures: ImmutableList<String>, onImageClick: (index: Int) -> Unit,
) {
    Text(
        modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 4.dp),
        text = stringResource(R.string.pictures),
        style = MaterialTheme.typography.titleLarge
    )

    LazyRow(
        contentPadding = PaddingValues(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(pictures) { index, link ->
            SubcomposeAsyncImage(
                modifier = Modifier
                    .height(192.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .clickable { onImageClick(index + 1) },
                model = ImageRequest.Builder(LocalContext.current).data(link).size(Size.ORIGINAL)
                    .build(),
                loading = {
                    Box(
                        modifier = Modifier
                            .size(width = 144.dp, height = 192.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .placeholder(enabled = true)
                    )
                },
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }

        if (isLoading) {
            items(count = 5) {
                Box(
                    modifier = Modifier
                        .size(width = 144.dp, height = 192.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .placeholder(enabled = true)
                )
            }
        }
    }
}

@Preview
@Composable
private fun DetailsPicturePreview() {
    Surface {
        Column {
            DetailsPicturesView(
                isLoading = false,
                pictures = persistentListOf("", "", ""),
                onImageClick = {})
        }
    }
}
