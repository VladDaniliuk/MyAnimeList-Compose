package shov.studio.ui.anime.ui.details.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import shov.studio.enums.RelationType
import shov.studio.enums.Season
import shov.studio.ui.anime.R
import shov.studio.ui.anime.data.models.DetailsRelatedModel

@Composable
internal fun DetailsRelatedView(
    text: String,
    list: ImmutableList<DetailsRelatedModel>,
    onRelatedClick: () -> Unit,
    onRelatedItemClick: (id: Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = text,
            style = MaterialTheme.typography.titleLarge,
        )

        if (list.size > 3) TextButton(onClick = onRelatedClick) {
            Text(text = stringResource(R.string.show_all))
        }
    }

    list.take(3).forEach { response ->
        RelatedAnimeItem(response) {
            onRelatedItemClick(response.id)
        }
    }
}

@Preview
@Composable
private fun DetailsRelatedPreview() {
    Surface {
        Column {
            DetailsRelatedView(
                text = "Text",
                list = persistentListOf(
                    DetailsRelatedModel(
                        0,
                        "Title",
                        "",
                        2022,
                        Season.spring,
                        9f,
                        RelationType.full_story
                    )
                ),
                onRelatedClick = {},
                onRelatedItemClick = {}
            )
        }
    }
}
