package core.utils

import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T : Any, R : Any> Flow<PagingData<T>>.mapResponse(
    transform: T.() -> R,
): Flow<PagingData<R>> = map { data -> data.map { response -> transform(response) } }
