package com.alekseilomain.presentation.contacts

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alekseilomain.domain.model.Contact

class ListPagingSource(
    private val items: List<Contact>
) : PagingSource<Int, Contact>() {

    override fun getRefreshKey(state: PagingState<Int, Contact>): Int? = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Contact> {
        val page = params.key ?: 0
        val from = page * params.loadSize
        val to = minOf(from + params.loadSize, items.size)

        return LoadResult.Page(
            data = items.subList(from, to),
            prevKey = if (page == 0) null else page - 1,
            nextKey = if (to < items.size) page + 1 else null
        )
    }
}
