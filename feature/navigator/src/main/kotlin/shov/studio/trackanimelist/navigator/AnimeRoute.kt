package shov.studio.trackanimelist.navigator

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ListAlt
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.ImportContacts
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.SupervisedUserCircle
import androidx.compose.ui.graphics.vector.ImageVector
import shov.studio.enums.Season
import shov.studio.enums.Sort
import shov.studio.enums.SortType
import shov.studio.enums.Status
import shov.studio.enums.DetailsStatus
import shov.studio.navigator.R
import shov.studio.trackanimelist.navigator.arguments.toArgument

sealed class AnimeRoute(val route: String) {
    data object Main : AnimeRoute("main") {
        enum class MainNavBar(
            @StringRes val titleId: Int,
            val icon: ImageVector,
            val route: String,
        ) {
            Anime(R.string.anime, Icons.Rounded.Movie, Type.anime),
            Manga(R.string.manga, Icons.Rounded.ImportContacts, Type.manga),
            Suggested(R.string.suggested, Icons.Rounded.Favorite, "suggested"),
            UserList(R.string.user_list, Icons.AutoMirrored.Rounded.ListAlt, "user_list"),
            User(R.string.user, Icons.Rounded.SupervisedUserCircle, "user");
        }
    }

    data object Filter : AnimeRoute("filter")
    data object FilterResult : AnimeRoute("filter_result/{year}/{season}/{sort}") {
        fun withArgs(year: String, season: Season, sortType: SortType) =
            "filter_result/$year/${season.name}/$sortType"
    }

    data object Details : AnimeRoute("details/{type}/{id}") {
        fun withTypeAndId(type: String, id: Int) = "details/$type/$id"

        data object Info : AnimeRoute("info")

        data object AddTag : AnimeRoute("add_tag/{type}/{id}?tag={tags}") {
            fun withTypeIdAndTags(type: String, id: Int, tags: List<String>) =
                "add_tag/$type/$id${tags.toArgument("tag")}"
        }

        data object DeleteTag : AnimeRoute("delete_tag/{type}/{id}/{tag}?tags={tags}") {
            fun withTypeIdAndTag(type: String, id: Int, tag: String, tags: List<String>) =
                "delete_tag/$type/$id/$tag${tags.toArgument("tags")}"
        }

        data object SetScore : AnimeRoute("set_score/{type}/{id}/{score}") {
            fun withTypeIdAndScore(type: String, id: Int, score: Int) =
                "set_score/$type/$id/$score"
        }

        data object AddToList : AnimeRoute("add_to_list/{type}/{id}/{status}") {
            fun withTypeIdAndStatus(type: String, id: Int, status: Status) =
                "add_to_list/$type/$id/$status"
        }

        data object SetChaptersRead : AnimeRoute("set_chapters_read/{type}/{id}/{status}/{max}") {
            fun withTypeIdAndChapters(type: String, id: Int, status: DetailsStatus, max: Int) =
                "set_chapters_read/$type/$id/$status/$max"
        }

        data object SetVolumesRead : AnimeRoute("set_volumes_read/{type}/{id}/{status}/{max}") {
            fun withTypeIdAndVolumes(type: String, id: Int, status: DetailsStatus, max: Int) =
                "set_volumes_read/$type/$id/$status/$max"
        }

        data object SetEpisodesWatched :
            AnimeRoute("set_episodes_watched/{type}/{id}/{status}/{max}") {
            fun withTypeIdAndVolumes(type: String, id: Int, status: DetailsStatus, max: Int) =
                "set_episodes_watched/$type/$id/$status/$max"
        }
    }

    data object DetailsNames : AnimeRoute("details_names/{type}/{id}") {
        fun withTypeAndId(type: String, id: Int) = "details_names/$type/$id"
    }

    data object Images : AnimeRoute("images/{type}/{id}/{index}") {
        fun withTypeIdAndIndex(type: String, id: Int, index: Int) = "images/$type/$id/$index"
    }

    data object Related : AnimeRoute("related/{type}/{id}") {
        fun withTypeAndId(type: String, id: Int) = "related/$type/$id"
    }

    data object UserList : AnimeRoute("user_list/{type}") {
        fun withType(type: String) = "user_list/$type"
    }

    data object UserListSort : AnimeRoute("user_list_sort/{type}/{sort}") {
        fun withTypeAndSort(type: String, sort: Sort) = "user_list_sort/$type/$sort"
    }

    data object AccountManagement : AnimeRoute("account_management")

    data object LogOut : AnimeRoute("log_out/{id}") {
        fun withId(id: String) = "log_out/$id"
    }

    data object AddToList : AnimeRoute("add_to_list/{type}/{id}") {
        fun withTypeAndId(type: String, id: Int) = "add_to_list/$type/$id"
    }

    object Args {
        const val episodes = "episodes"
        const val status = "status"
        const val id = "id"
        const val index = "index"
        const val season = "season"
        const val sort = "sort"
        const val type = "type"
        const val year = "year"
        const val tag = "tag"
        const val tags = "tags"
        const val addedTag = "added_tag"
        const val deletedTag = "deleted_tag"
        const val score = "score"
        const val max = "max"
        const val volumes = "volumes"
        const val chapters = "chapters"
    }

    object Type {
        const val anime = "anime"
        const val manga = "manga"
    }
}
