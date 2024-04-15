package core.navigator.utils

import androidx.lifecycle.SavedStateHandle

fun <T> SavedStateHandle.getAndRemove(key: String, onSuccess: (T) -> Unit) {
    get<T>(key)?.let { value ->
        onSuccess(value)

        remove<T>(key)
    }
}