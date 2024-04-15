package core.ui.error

fun Throwable.toErrorString() = localizedMessage ?: message ?: toString()
