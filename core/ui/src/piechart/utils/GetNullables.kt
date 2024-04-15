package core.ui.piechart.utils

import kotlinx.collections.immutable.ImmutableList
import core.ui.piechart.PieData

fun ImmutableList<PieData>.getNullables() = sumOf { data -> if (data.value == 0f) 1f else 0f }
