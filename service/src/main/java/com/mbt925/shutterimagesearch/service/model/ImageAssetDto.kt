package com.mbt925.shutterimagesearch.service.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageAssetDto(
    @SerialName("url")
    val url: String,
)
