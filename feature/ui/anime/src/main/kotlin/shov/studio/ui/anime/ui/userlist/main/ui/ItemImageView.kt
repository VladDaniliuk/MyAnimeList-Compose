package shov.studio.ui.anime.ui.userlist.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.size.Size
import shov.studio.ui.anime.data.models.DetailsStandardModel
import shov.studio.ui.anime.ui.userlist.main.UserListContract
import core.ui.common.modifiers.placeholder

@Composable
internal fun ItemImageView(
    event: (UserListContract.Event) -> Unit,
    type: String,
    details: DetailsStandardModel,
) {
    Column(
        modifier = Modifier
            .width(96.dp)
            .clip(MaterialTheme.shapes.small)
            .clickable {
                event(UserListContract.Event.OnItemClick(type, details.id))
            }
            .padding(all = 4.dp)
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(details.picture).size(Size.ORIGINAL)
                .build()
        )

        Image(
            modifier = Modifier
                .height(128.dp)
                .clip(MaterialTheme.shapes.medium)
                .align(Alignment.CenterHorizontally)
                .placeholder(enabled = painter.state !is AsyncImagePainter.State.Success),
            painter = painter,
            contentDescription = details.title,
            contentScale = ContentScale.Crop
        )

        Text(
            modifier = Modifier
                .wrapContentWidth()
                .padding(top = 4.dp),
            text = details.title,
            style = MaterialTheme.typography.titleSmall,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemImagePreview() {
    ItemImageView(event = {}, type = "anime", details = DetailsStandardModel(0, "", null, null, ""))
}
