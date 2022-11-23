package com.mbt925.shutterimagesearch.service.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageAssetsDto(
    @SerialName("preview")
    val preview: ImageAssetDto,
    @SerialName("small_thumb")
    val small_thumb: ImageAssetDto,
    @SerialName("large_thumb")
    val large_thumb: ImageAssetDto,
)
