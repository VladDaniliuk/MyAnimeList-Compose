package shov.studio.trackanimelist.navigator.arguments

fun List<String>?.toArgument(key: String) = this?.let { list ->
    if (list.isEmpty()) "" else "?$key=${list[0]}" + list.drop(1)
        .joinToString(separator = "") { element -> "&$key=$element" }
} ?: ""
