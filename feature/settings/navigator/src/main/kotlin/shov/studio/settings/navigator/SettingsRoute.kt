package shov.studio.settings.navigator

sealed class SettingsRoute(val route: String) {
    data object Settings : SettingsRoute("settings")
}
