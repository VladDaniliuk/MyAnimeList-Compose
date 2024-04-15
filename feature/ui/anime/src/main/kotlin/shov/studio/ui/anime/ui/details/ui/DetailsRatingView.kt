package shov.studio.ui.anime.ui.details.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import shov.studio.ui.anime.R
import shov.studio.ui.anime.data.models.DetailsFullModel
import shov.studio.ui.anime.ui.details.DetailsContract
import core.ui.common.modifiers.placeholder

@Composable
internal fun DetailsRatingView(details: DetailsFullModel, isLoading: Boolean) {
    if ((details.rating != null) or (((details.rank != null) or (details.popularity != null)
                or (details.usersListing != null) or (details.usersScoring != null)))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            details.rating?.let {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.rating_text),
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .placeholder(enabled = isLoading)
                            .wrapContentWidth(Alignment.CenterHorizontally),
                        text = details.rating.toString(),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            Card(modifier = Modifier.weight(2f)) {
                Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
                    if ((details.rank != null) or (details.popularity != null)
                        or (details.usersListing != null) or (details.usersScoring != null)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            if (details.rank != null) Text(
                                text = stringResource(R.string.rank),
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                            )

                            if (details.popularity != null) Text(
                                text = stringResource(R.string.popularity),
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                            )

                            if (details.usersListing != null) Text(
                                text = stringResource(R.string.users_listing),
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                            )

                            if (details.usersScoring != null) Text(
                                text = stringResource(R.string.users_scoring),
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                            )
                        }

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .placeholder(
                                    enabled = isLoading,
                                    backgroundColor = CardDefaults.cardColors().containerColor
                                ),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (details.rank != null) Text(text = details.rank.toString())

                            if (details.popularity != null) Text(text = details.popularity.toString())

                            if (details.usersListing != null) Text(text = details.usersListing.toString())

                            if (details.usersScoring != null) Text(text = details.usersScoring.toString())
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun DetailsRatingPreview() {
    Surface {
        DetailsRatingView(
            details = DetailsContract.State("anime", isAuthorized = true).details,
            isLoading = false
        )
    }
}