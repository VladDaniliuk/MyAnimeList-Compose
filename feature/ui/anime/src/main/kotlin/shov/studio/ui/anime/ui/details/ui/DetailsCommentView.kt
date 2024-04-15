package shov.studio.ui.anime.ui.details.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import shov.studio.ui.anime.ui.details.DetailsContract
import core.ui.preview.PreviewScreenSizes

@Composable
internal fun DetailsCommentView(
    detailsState: DetailsContract.State,
    event: (DetailsContract.Event) -> Unit,
) {
    AnimatedContent(
        targetState = detailsState.isCommentEditable,
        label = "Comment visibility",
        transitionSpec = {
            if (targetState) slideInHorizontally { it } + fadeIn() togetherWith
                    slideOutHorizontally { -it } + fadeOut()
            else slideInHorizontally { -it } + fadeIn() togetherWith
                    slideOutHorizontally { it } + fadeOut()
        }
    ) { isEditable ->
        if (isEditable) {
            DetailsCommentEditorView(detailsState = detailsState, event = event)
        } else {
            CommentView(event = event, detailsState = detailsState)
        }
    }
}

@PreviewScreenSizes
@Composable
private fun DetailsCommentPreview() {
    DetailsCommentEditorView(event = {}, detailsState = DetailsContract.State("anime"))
}
