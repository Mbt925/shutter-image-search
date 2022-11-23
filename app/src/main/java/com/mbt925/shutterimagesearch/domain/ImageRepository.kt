package com.mbt925.shutterimagesearch.domain

import androidx.paging.PagingData
import com.mbt925.shutterimagesearch.domain.model.Image
import kotlinx.coroutines.flow.Flow

interface ImageRepository {

    fun searchImages(query: String): Flow<PagingData<Image>>
}
