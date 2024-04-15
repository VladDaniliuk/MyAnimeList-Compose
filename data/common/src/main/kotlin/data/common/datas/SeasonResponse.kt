package data.common.datas

import kotlinx.serialization.Serializable
import shov.studio.enums.Season

@Serializable
data class SeasonResponse(val year: Int, val season: Season)
