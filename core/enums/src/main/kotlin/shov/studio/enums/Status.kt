package shov.studio.enums

import androidx.annotation.StringRes

enum class Status(@StringRes val id: Int) {
    delete(R.string.remove), not_watching(R.string.not_watching), watching(R.string.watching),
    completed(R.string.completed), on_hold(R.string.on_hold), dropped(R.string.dropped),
    plan_to_watch(R.string.plan_to_watched), not_reading(R.string.not_reading),
    reading(R.string.reading), plan_to_read(R.string.plan_to_read)
}
