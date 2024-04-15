package core.ui.piechart

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import core.ui.piechart.utils.getCoefficient

@Composable
fun PieChart(
    pieState: PieState,
    pieData: PersistentList<PieData>,
) {
    val isNotVisible = pieData.all { it.value == 0f }

    val verticalPadding by animateDpAsState(
        targetValue = if (isNotVisible) 0.dp else 32.dp,
        label = "vertical padding value for visible pie chart"
    )
    val horizontalPadding by animateDpAsState(
        targetValue = if (isNotVisible) 0.dp else 64.dp,
        label = "horizontal padding value for visible pie chart"
    )
    val visibility by animateFloatAsState(
        targetValue = if (isNotVisible.not()) 1f else 0f,
        label = "visibility animation chart"
    )

    val rotation by rememberInfiniteTransition(label = "infinite chart animation").animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(animation = tween(durationMillis = 2000)),
        label = " infinite chart rotation"
    )

    val color = MaterialTheme.colorScheme.onBackground
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .fillMaxWidth(visibility)
                .padding(horizontal = horizontalPadding, vertical = verticalPadding)
                .aspectRatio(1f)
                .rotate(if (pieState == PieState.Loading) rotation else 0f)
                .drawWithContent {
                    pieData.forEachIndexed { index, pie ->
                        drawArc(
                            color = if (pieState == PieState.Loading) color else pie.color,
                            startAngle = pieData
                                .subList(0, index)
                                .map {
                                    it.value * pieData.getCoefficient() +
                                            (if (it.value != 0f) 14f else 0f)
                                }
                                .sum(),
                            sweepAngle = pieData.getCoefficient() * pie.value,
                            useCenter = false,
                            style = Stroke(24.dp.toPx(), cap = StrokeCap.Round)
                        )
                    }
                }
        )
    }
}

@Preview
@Composable
fun PieChartPreview() {
    Surface {
        PieChart(
            pieState = PieState.Complete,
            pieData = persistentListOf(
                PieData(MaterialTheme.colorScheme.onBackground, 12f),
                PieData(MaterialTheme.colorScheme.primary, 18f),
                PieData(MaterialTheme.colorScheme.inversePrimary, 24f),
                PieData(MaterialTheme.colorScheme.secondary, 32f),
                PieData(MaterialTheme.colorScheme.tertiary, 36f)
            )
        )
    }
}
