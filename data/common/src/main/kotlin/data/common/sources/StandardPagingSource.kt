package data.common.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import data.common.datas.DataStandardResponse
import data.common.datas.StandardResponse

class StandardPagingSource(
    private val getResponse: suspend (Int) -> Result<StandardResponse>,
) : PagingSource<Int, DataStandardResponse>() {
    override fun getRefreshKey(state: PagingState<Int, DataStandardResponse>): Int? {
        val anchorPosition = state.anchorPosition ?: return null

        return state.closestPageToPosition(anchorPosition)?.prevKey?.plus(other = 10)
            ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(other = 10)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataStandardResponse> {
        val key = params.key ?: 0
        return getResponse(key).fold(
            onSuccess = { response ->
                LoadResult.Page(
                    data = response.data,
                    prevKey = response.paging.previous?.let { key.minus(other = 10) },
                    nextKey = response.paging.next?.let { key.plus(other = 10) }
                )
            },
            onFailure = { error -> LoadResult.Error(error) }
        )
    }
}
