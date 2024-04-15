package shov.studio.ui.ui.settings

import android.net.Uri
import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import shov.studio.core.data.Theme
import core.mvi.UnidirectionalViewModel
import shov.studio.feature.ui.models.data.models.UserModel
import shov.studio.ui.ui.settings.SettingsContract.Effect
import shov.studio.ui.ui.settings.SettingsContract.Event
import shov.studio.ui.ui.settings.SettingsContract.State

internal interface SettingsContract : UnidirectionalViewModel<State, Event, Effect> {
    @Immutable
    data class State(
        val status: Status = Status.LOADING,
        val user: UserModel = UserModel("", "", null),
        val otherAccounts: ImmutableList<UserModel> = persistentListOf(),
        val error: String? = null,
        val theme: Theme = Theme.DARK,
        val isDynamicColor: Boolean = true,
        val isThemeExpanded: Boolean = false,
        val isAuthorized: Boolean = false,
        val isListOpened: Boolean = false,
    ) {
        val isPlaceholder = status != Status.COMPLETE
    }

    enum class Status {
        LOADING, NEED_AUTH, AUTH_ERROR, COMPLETE
    }

    @Immutable
    sealed interface Event {
        data object OnBackClick : Event
        data class OnThemExpandClick(val isExpanded: Boolean) : Event
        data class OnThemeClick(val theme: Theme) : Event
        data object OnLogInClick : Event
        data object OnAddNewAccountClick : Event
        data class OnDynamicColorChange(val isDynamic: Boolean) : Event
        data object OnUserClick : Event
        data object OnUserListClick : Event
        data class OnUserChangeClick(val id: String) : Event
        data object OnUriClick : Event
        data object OnAccountManagementClick : Event
    }

    sealed interface Effect {
        data object OnBack : Effect
        data class OnLogIn(val uri: Uri) : Effect
        data object OnUser : Effect
        data object OnUriOpen : Effect
        data object OnAccountManagementOpen : Effect
        data object OnAccountChanged:Effect
    }
}
