package core.ui.common.button

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.ripple
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedIconButton(
    isVisible: Boolean,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val size by animateDpAsState(
        targetValue = if (isVisible) 48.dp else 0.dp,
        label = "Animated Icon Button size"
    )

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .clickable(
                onClick = onClick,
                enabled = isVisible,
                role = Role.Button,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(
                    bounded = false,
                    radius = 20.dp
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        CompositionLocalProvider(LocalContentColor provides LocalContentColor.current) {
            Icon(
                imageVector = icon,
                contentDescription = icon.name
            )
        }
    }
}

@Preview
@Composable
fun AnimatedIconButtonPreview() {
    AnimatedIconButton(isVisible = true, onClick = {}, icon = Icons.AutoMirrored.Rounded.ArrowBack)
}

@Preview
@Composable
fun AnimatedIconButtonPreview2() {
    AnimatedIconButton(isVisible = false, onClick = {}, icon = Icons.AutoMirrored.Rounded.ArrowBack)
}
