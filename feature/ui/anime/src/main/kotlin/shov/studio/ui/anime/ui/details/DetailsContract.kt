package shov.studio.ui.anime.ui.details

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import shov.studio.enums.DetailsStatus
import core.mvi.UnidirectionalViewModel
import shov.studio.trackanimelist.navigator.AnimeRoute
import shov.studio.ui.anime.R
import shov.studio.ui.anime.data.models.DetailsFullModel
import shov.studio.ui.anime.data.models.ListStatusModel
import shov.studio.ui.anime.ui.details.DetailsContract.Effect
import shov.studio.ui.anime.ui.details.DetailsContract.Event
import shov.studio.ui.anime.ui.details.DetailsContract.State
import shov.studio.enums.Status as AnimeStatus

internal interface DetailsContract : UnidirectionalViewModel<State, Event, Effect> {
    @Immutable
    data class State(
        val type: String,
        val details: DetailsFullModel = DetailsFullModel(type),
        val isAuthorized: Boolean = false,
        val status: Status = Status.LOADING,
        val error: Error? = null,
        val editedComment: String = "",
        val isCommentEditable: Boolean = details.listStatus.comments.isEmpty(),
        val isCommentMenuOpened: Boolean = false,
        val isConnected: Boolean = true,
    ) {
        val snackbarHostState = SnackbarHostState()
        val isLoading = status == Status.LOADING
        val isAnime = type == AnimeRoute.Type.anime

        @ReadOnlyComposable
        @Composable
        fun startDate(): String = details.startYear?.let { year ->
            details.startSeason?.let { season ->
                stringResource(R.string.studios_list, stringResource(season.id), year)
            } ?: year.toString()
        } ?: ""

        @ReadOnlyComposable
        @Composable
        fun episodes() = listOfNotNull(
            details.numEpisodes?.let { ep -> stringResource(R.string.episodes, ep) },
            details.numVolumes?.let { vol -> stringResource(R.string.volumes, vol) },
            details.numChapters?.let { ch -> stringResource(R.string.chapters, ch) }
        ).joinToString(separator = stringResource(R.string.separator))

        @ReadOnlyComposable
        @Composable
        fun studios() = details.studios.joinToString(stringResource(R.string.separator))

        @ReadOnlyComposable
        @Composable
        fun authors(): String {
            val resources = LocalContext.current.resources

            return details.authors.joinToString(stringResource(R.string.separator)) { author ->
                resources
                    .getString(R.string.author_data, author.firstName, author.lastName, author.role)
            }
        }

        data class Error(val error: String, val onError: () -> Unit)

        fun loading() = copy(status = Status.LOADING, error = null)
        fun refresh() = copy(status = Status.REFRESHING, error = null)
        fun complete(details: DetailsFullModel) =
            copy(
                details = details,
                status = Status.COMPLETE,
                error = null,
                isCommentEditable = details.listStatus.comments.isEmpty()
            )

        fun error(error: String, onError: () -> Unit) = copy(
            status = if (status == Status.REFRESHING) Status.FULL_ERROR else Status.EMPTY_ERROR,
            error = Error(error, onError)
        )

        fun closeError() =
            if (status == Status.FULL_ERROR) copy(status = Status.COMPLETE, error = null) else this

        fun details(
            status: AnimeStatus = details.listStatus.status,
            score: Int = details.listStatus.score,
            numVolumesRead: Int? = details.listStatus.numVolumesRead,
            numChaptersRead: Int? = details.listStatus.numChaptersRead,
            isRewatching: Boolean = details.listStatus.isRewatching,
            tags: List<String> = details.listStatus.tags,
            comments: String = details.listStatus.comments,
            numEpisodesWatched: Int? = details.listStatus.numEpisodesWatched,
        ) = copy(
            details = details.copy(
                listStatus = ListStatusModel(
                    status = status,
                    score = score,
                    numVolumesRead = numVolumesRead,
                    numChaptersRead = numChaptersRead,
                    isRewatching = isRewatching,
                    tags = tags,
                    comments = comments,
                    numEpisodesWatched = numEpisodesWatched
                )
            )
        )
    }

    enum class Status {
        LOADING, EMPTY_ERROR, COMPLETE, REFRESHING, FULL_ERROR
    }

    @Immutable
    sealed class Event {
        data object OnBackClick : Event()
        data class OnIconClick(val index: Int) : Event()
        data object OnNameClick : Event()
        data object OnAddToListClick : Event()
        data class OnTagDeleteClick(val tag: String) : Event()
        data object OnTagAddClick : Event()
        data class OnRelatedClick(val type: String) : Event()
        data class OnAnimeClick(val id: Int) : Event()
        data class OnMangaClick(val id: Int) : Event()
        data class OnDetailsClick(val id: Int) : Event()
        data object OnRefreshClick : Event()
        data class OnRewathcingClick(val isRewatching: Boolean) : Event()
        data class OnCommentChange(val comment: String) : Event()
        data object OnCommentSetClick : Event()
        data object OnCommentDeleteClick : Event()
        data object OnCommentChangeClick : Event()
        data object OnCommentMenuOpenClick : Event()
        data object OnCommentMenuCloseClick : Event()
        data object OnSetScoreClick : Event()
        data object OnErrorClick : Event()
        data object OnChaptersReadClick : Event()
        data object OnVolumesReadClick : Event()
        data object OnEpisodesWatchedClick : Event()
    }

    sealed class Effect {
        data object OnBack : Effect()
        data class OnIconOpen(val type: String, val id: Int, val index: Int) : Effect()
        data class OnNameOpen(val type: String, val id: Int) : Effect()
        data class OnAddToList(
            val type: String,
            val id: Int,
            val status: AnimeStatus,
        ) : Effect()

        data class OnTagDelete(
            val type: String, val id: Int, val tag: String, val tags: List<String>,
        ) : Effect()

        data class OnTagAdd(val type: String, val id: Int, val tags: List<String>) : Effect()
        data class OnRelatedOpen(val type: String) : Effect()
        data class OnAnimeOpen(val id: Int) : Effect()
        data class OnMangaOpen(val id: Int) : Effect()
        data class OnSetScoreOpen(val type: String, val id: Int, val score: Int) : Effect()
        data class OnErrorShow(val error: String, val onError: () -> Unit) : Effect()
        data class OnChaptersRead(
            val type: String, val id: Int, val status: DetailsStatus, val max: Int,
        ) : Effect()

        data class OnVolumesRead(
            val type: String, val id: Int, val status: DetailsStatus, val max: Int,
        ) : Effect()

        data class OnEpisodesWatched(
            val type: String, val id: Int, val status: DetailsStatus, val max: Int,
        ) : Effect()

        data object OnRefreshStop : Effect()
    }
}
