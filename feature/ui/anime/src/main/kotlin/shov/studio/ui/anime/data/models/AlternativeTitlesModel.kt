package shov.studio.ui.anime.data.models

import kotlinx.collections.immutable.ImmutableList

internal data class AlternativeTitlesModel(
    val title: String,
    val en: String,
    val ja: String,
    val synonyms: ImmutableList<String>,
)
