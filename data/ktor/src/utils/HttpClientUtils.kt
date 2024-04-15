package data.ktor.utils

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.url

suspend inline fun <reified T> HttpClient.get(
    urlString: String,
    block: HttpRequestBuilder.() -> Unit = {},
): Result<T> = try {
    Result.success(get { url(urlString); block() }.body<T>())
} catch (e: Throwable) {
    Result.failure(e)
}

suspend inline fun <reified T> HttpClient.post(
    urlString: String,
    block: HttpRequestBuilder.() -> Unit = {},
): Result<T> = try {
    Result.success(post { url(urlString); block() }.body<T>())
} catch (e: Throwable) {
    Result.failure(e)
}

suspend inline fun <reified T> HttpClient.patch(
    urlString: String,
    block: HttpRequestBuilder.() -> Unit = {},
): Result<T> = try {
    Result.success(patch { url(urlString); block() }.body<T>())
} catch (e: Throwable) {
    Result.failure(e)
}

suspend inline fun <reified T> HttpClient.delete(
    urlString: String,
    block: HttpRequestBuilder.() -> Unit = {},
): Result<T> = try {
    Result.success(patch { url(urlString); block() }.body<T>())
} catch (e: Throwable) {
    Result.failure(e)
}
