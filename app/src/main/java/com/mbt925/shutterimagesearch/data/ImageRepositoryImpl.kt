package com.mbt925.shutterimagesearch.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.mbt925.shutterimagesearch.data.mapper.toImage
import com.mbt925.shutterimagesearch.domain.ImageRepository
import com.mbt925.shutterimagesearch.service.ImagesService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.map

class ImageRepositoryImpl(
    private val dataSource: ImageRemoteDataSource,
    private val dispatcher: CoroutineDispatcher,
) : ImageRepository {

    override fun searchImages(query: String) = Pager(
        config = PagingConfig(
            pageSize = ImagesService.PAGE_SIZE,
            enablePlaceholders = false,
        ),
        pagingSourceFactory = {
            ImagePagingSource(
                query = query,
                dataSource = dataSource,
                dispatcher = dispatcher,
            )
        },
    )
        .flow
        .map { it.map { dto -> dto.toImage() } }

}
