package com.mbt925.shutterimagesearch.service.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImagesPageDto(
    @SerialName("data")
    val images: List<ImageDto>,
    @SerialName("page")
    val page: Int,
    @SerialName("total_count")
    val totalCount: Int,
)
