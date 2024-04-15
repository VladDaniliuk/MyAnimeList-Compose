package shov.studio.domain.core.data.models

data class StatusEntity(
    val watching: Int,
    val completed: Int,
    val onHold: Int,
    val dropped: Int,
    val planToWatch: Int,
)
