package shov.studio.ui.anime.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import shov.studio.ui.anime.R
import shov.studio.ui.anime.data.models.DetailsStandardModel
import core.ui.common.modifiers.placeholder

@Composable
internal fun AnimeItem(
    details: DetailsStandardModel?,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isAuthorized: Boolean = false,
    onAddToListClick: (id: Int) -> Unit,
    onItemClick: (id: Int) -> Unit,
) {
    details?.let {
        Row(
            modifier = modifier
                .clickable { onItemClick(details.id) }
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(details.picture)
                    .crossfade(true)
                    .build(),
                contentDescription = details.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 96.dp, height = 128.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .placeholder(enabled = isLoading)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = details.title,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .placeholder(enabled = isLoading)
                )

                details.rating?.let { mean ->
                    Text(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .fillMaxWidth()
                            .placeholder(enabled = isLoading),
                        text = "$mean/10",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Text(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .fillMaxWidth()
                        .placeholder(enabled = isLoading),
                    text = details.synopsis,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            var isExpanded by remember { mutableStateOf(false) }

            if (isAuthorized and isLoading.not()) {
                Column {
                    IconButton(onClick = { isExpanded = true }) {
                        Icon(
                            imageVector = Icons.Rounded.MoreVert,
                            contentDescription = Icons.Rounded.MoreVert.name
                        )
                    }

                    DropdownMenu(
                        expanded = isExpanded,
                        onDismissRequest = { isExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = stringResource(R.string.add_to_list)) },
                            onClick = {
                                isExpanded = false
                                onAddToListClick(details.id)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RelatedAnimePreview(
    @PreviewParameter(RelatedAnimePreviewParameterProvider::class) parameter: Pair<Boolean, Boolean>,
) {
    val (isLoading, isAuthorized) = parameter
    @Suppress("MaxLineLength")
    AnimeItem(
        details = DetailsStandardModel(
            id = 34134,
            title = "One Punch Man 2nd Season",
            picture = "https://api-cdn.myanimelist.net/images/anime/1247/122044.jpg",
            rating = 8.5f,
            synopsis = "The seemingly unimpressive Saitama has a rather unique hobby: being a hero. In order to pursue his childhood dream, Saitama relentlessly trained for three years, losing all of his hair in the process. Now, Saitama is so powerful, he can defeat any enemy with just one punch. However, having no one capable of matching his strength has led Saitama to an unexpected problem—he is no longer able to enjoy the thrill of battling and has become quite bored.\n\nOne day, Saitama catches the attention of 19-year-old cyborg Genos, who witnesses his power and wishes to become Saitama's disciple. Genos proposes that the two join the Hero Association in order to become certified heroes that will be recognized for their positive contributions to society. Saitama, who is shocked that no one knows who he is, quickly agrees. Meeting new allies and taking on new foes, Saitama embarks on a new journey as a member of the Hero Association to experience the excitement of battle he once felt.\n\n[Written by MAL Rewrite]"
        ),
        onAddToListClick = {},
        isLoading = isLoading,
        isAuthorized = isAuthorized
    ) {}
}

class RelatedAnimePreviewParameterProvider : PreviewParameterProvider<Pair<Boolean, Boolean>> {
    override val values = sequenceOf(true to true, false to true, false to false)
}
