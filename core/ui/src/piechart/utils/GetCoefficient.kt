package core.ui.piechart.utils

import kotlinx.collections.immutable.ImmutableList
import core.ui.piechart.PieData

fun ImmutableList<PieData>.getCoefficient() = when (getNullables()) {
    in 0f..<size - 1f -> 360f - (size - getNullables()) * 14
    size - 1f, size.toFloat() -> 360f
    else -> throw IllegalArgumentException()
} / sumOf(PieData::value)
