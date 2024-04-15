package shov.studio.ui.user.ui.user.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.size.Size
import core.ui.common.modifiers.placeholder

@Composable
fun ColumnScope.UserImageView(
    picture: String?,
    name: String,
    isPlaceholderVisible: Boolean,
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(picture)
            .size(Size.ORIGINAL)
            .build()
    )

    if ((picture?.isEmpty() != false) and isPlaceholderVisible.not()) Icon(
        modifier = Modifier
            .statusBarsPadding()
            .align(Alignment.CenterHorizontally)
            .size(192.dp),
        imageVector = Icons.Rounded.Face,
        contentDescription = name,
    ) else Image(
        modifier = Modifier
            .statusBarsPadding()
            .align(Alignment.CenterHorizontally)
            .size(192.dp)
            .clip(CircleShape)
            .placeholder(enabled = painter.state !is AsyncImagePainter.State.Success),
        painter = painter,
        contentDescription = name,
        contentScale = ContentScale.Crop
    )

    Text(
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier
            .padding(top = 8.dp)
            .align(Alignment.CenterHorizontally)
            .placeholder(enabled = isPlaceholderVisible),
        text = name
    )
}

@Preview
@Composable
fun UserImagePreview() {
    Surface {
        Column {
            UserImageView(picture = "", name = "Long Long Name", isPlaceholderVisible = false)
        }
    }
}