package shov.studio.ui.anime.ui.addtolist

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import shov.studio.enums.DetailsStatus
import core.mvi.UnidirectionalViewModel
import shov.studio.ui.anime.ui.addtolist.AddToListContract.Effect
import shov.studio.ui.anime.ui.addtolist.AddToListContract.Event
import shov.studio.ui.anime.ui.addtolist.AddToListContract.State
import shov.studio.enums.Status as UserStatus

interface AddToListContract : UnidirectionalViewModel<State, Event, Effect> {
    @Stable
    data class State(
        val isAnime: Boolean,
        val tags: ImmutableList<String> = persistentListOf(),
        val tag: String = "",
        val status: Status = Status.LOADING,
        val userStatus: UserStatus? = null,
        val isRewatching: Boolean = false,
        val score: Float = 0f,
        val episodes: Int? = null,
        val maxEpisodes: Int? = null,
        val chapters: Int? = null,
        val maxChapters: Int? = null,
        val volumes: Int? = null,
        val maxVolumes: Int? = null,
        val comment: String = "",
        val error: String? = null,
        val isAddTagOpen: Boolean = false,
        val isStatusMenuOpen: Boolean = false,
        val detailsStatus: DetailsStatus =
            if (isAnime) DetailsStatus.not_yet_aired else DetailsStatus.not_yet_published,
    ) {
        val isClickable = (status == Status.COMPLETE) or (status == Status.UPDATE_ERROR)
        val isLoading = status == Status.LOADING
        val isUpdating = (status == Status.LOADING) or (status == Status.UPDATE)
        val isError = status == Status.ERROR
        val isUpdateError = status == Status.UPDATE_ERROR
        val isEpisodesEditable = maxEpisodes == 0 && detailsStatus != DetailsStatus.not_yet_aired
                && detailsStatus != DetailsStatus.not_yet_published
        val isChaptersEditable = maxChapters == 0 && detailsStatus != DetailsStatus.not_yet_aired
                && detailsStatus != DetailsStatus.not_yet_published
        val isVolumesEditable = maxVolumes == 0 && detailsStatus != DetailsStatus.not_yet_aired
                && detailsStatus != DetailsStatus.not_yet_published
    }

    enum class Status {
        LOADING, ERROR, COMPLETE, UPDATE, UPDATE_ERROR
    }

    @Stable
    sealed interface Event {
        data class OnTagDeleteClick(val index: Int) : Event
        data object OnTagAddClick : Event
        data class OnTagChange(val tag: String) : Event
        data object OnTagSaveClick : Event
        data class OnStatusOpenChange(val isStatusMenuOpen: Boolean) : Event
        data class OnStatusClick(val status: UserStatus) : Event
        data class OnRewatchingClick(val isRewatching: Boolean) : Event
        data class OnScoreChange(val score: Float) : Event
        data class OnEpisodesChange(val episodes: String) : Event
        data class OnChaptersChange(val chapters: String) : Event
        data class OnVolumesChange(val volumes: String) : Event
        data class OnCommentChange(val comment: String) : Event
        data object OnCancelClick : Event
        data object OnConfirmClick : Event
        data object OnRetryClick : Event
    }

    sealed interface Effect {
        data object OnCancel : Effect
        data object OnComplete : Effect
    }
}
