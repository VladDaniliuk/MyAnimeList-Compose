package shov.studio.ui.anime.data.models

internal data class StatusModel(
    val watching: Int,
    val completed: Int,
    val onHold: Int,
    val dropped: Int,
    val planToWatch: Int,
)
