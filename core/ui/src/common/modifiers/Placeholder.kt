package core.ui.common.modifiers

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.DrawScope

@Composable
fun Modifier.placeholder(
    shape: Shape? = null,
    enabled: Boolean = true,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
): Modifier = if (enabled.not()) this else composed {
    val fade = Fade(backgroundColor.copy(alpha = 0.5f))
    val contentColor =
        contentColorFor(backgroundColor).copy(alpha = 0.3f).compositeOver(backgroundColor)
    val noNullShape = shape ?: MaterialTheme.shapes.small
    val highlightProgress by rememberInfiniteTransition(label = "placeholder-transition")
        .animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = fade.animationSpec,
            label = "placeholder-highlight-progress",
        )

    remember(contentColor, noNullShape, fade) {
        drawWithContent {
            drawPlaceholder(
                shape = noNullShape,
                color = contentColor,
                fade = fade,
                progress = highlightProgress,
            )
        }
    }
}

private fun DrawScope.drawPlaceholder(
    shape: Shape,
    color: Color,
    fade: Fade,
    progress: Float,
): Outline? {
    // Shortcut to avoid Outline calculation and allocation
    if (shape === RectangleShape) {
        drawRect(color) // draw the background color

        drawRect(fade.brush(), alpha = fade.alpha(progress))

        return null // we didn't (need to) draw an outline
    }

    return shape.createOutline(size, layoutDirection, this).also { outline ->
        drawOutline(outline, color)
        drawOutline(outline, fade.brush(), fade.alpha(progress))
    }
}

private class Fade(highlightColor: Color) {
    val animationSpec: InfiniteRepeatableSpec<Float> = infiniteRepeatable(
        animation = tween(delayMillis = 200, durationMillis = 600),
        repeatMode = RepeatMode.Reverse,
    )

    private val brush = SolidColor(highlightColor)

    fun brush(): Brush = brush
    fun alpha(progress: Float): Float = progress
}
