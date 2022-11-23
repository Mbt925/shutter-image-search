package com.mbt925.shutterimagesearch.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mbt925.shutterimagesearch.service.model.ImageDto
import com.mbt925.shutterimagesearch.networking.models.ApiResponse.Failure
import com.mbt925.shutterimagesearch.networking.models.ApiResponse.Success
import com.mbt925.shutterimagesearch.service.ImagesService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.invoke

class ImagePagingSource(
    private val query: String,
    private val dataSource: ImageRemoteDataSource,
    private val dispatcher: CoroutineDispatcher,
) : PagingSource<Int, ImageDto>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, ImageDto>) =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageDto> = dispatcher {
        val page = params.key ?: STARTING_PAGE_INDEX
        when (val response = dataSource.searchImages(query, page, ImagesService.PAGE_SIZE)) {
            is Success -> {
                val images = response.data?.images ?: emptyList()
                val prevKey = if (page > STARTING_PAGE_INDEX) page - 1 else null
                val nextPage = if (images.size < ImagesService.PAGE_SIZE) {
                    null
                } else {
                    page + 1
                }

                LoadResult.Page(
                    data = images,
                    prevKey = prevKey,
                    nextKey = nextPage
                )
            }
            is Failure.Known -> LoadResult.Error(
                Throwable("${response.error.code}: ${response.error.message}")
            )
            is Failure.Unknown -> LoadResult.Error(response.error)
        }
    }
}
