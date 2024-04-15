package data.common.datas

import kotlinx.serialization.Serializable

@Serializable
data class AlternativeTitlesDetailsResponse(val ja: String)

@Serializable
data class AlternativeTitlesFullResponse(val synonyms: List<String>, val en: String, val ja: String)
