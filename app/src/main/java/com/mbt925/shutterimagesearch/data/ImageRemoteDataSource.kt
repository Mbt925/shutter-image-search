package com.mbt925.shutterimagesearch.data

import com.mbt925.shutterimagesearch.service.model.ImagesPageDto
import com.mbt925.shutterimagesearch.networking.models.ApiResponse
import com.mbt925.shutterimagesearch.networking.executeApi
import com.mbt925.shutterimagesearch.service.ImagesService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.invoke

class ImageRemoteDataSource(
    private val service: ImagesService,
    private val dispatcher: CoroutineDispatcher,
) {

    suspend fun searchImages(
        query: String,
        page: Int,
        pageSize: Int,
    ): ApiResponse<ImagesPageDto> = dispatcher {
        service.searchImages(
            query = query,
            page = page,
            pageSize = pageSize,
        ).executeApi()
    }
}
