package com.issog.core.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.issog.core.data.Resources
import com.issog.core.data.source.remote.request.NewsRequest
import com.issog.core.domain.model.ArticleModel
import com.issog.core.domain.repository.IBeritainRepository
import kotlinx.coroutines.flow.first

class NewsPagingSource(
    private val beritainRepository: IBeritainRepository,
    private val newsRequest: NewsRequest
) : PagingSource<Int, ArticleModel>() {

    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleModel> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val request = newsRequest.copy(page = position, pageSize = params.loadSize)

            val response = beritainRepository.getTopHeadlineByCategory(request).first()
            val nextKey =
                if (response !is Resources.Success || response.data.isEmpty()) {
                    null
                } else {
                    position + 1
                }

            val data = if (response is Resources.Success) {
                response.data
            } else {
                emptyList()
            }

            LoadResult.Page(
                data = data,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
                ?: state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
        }
    }
}