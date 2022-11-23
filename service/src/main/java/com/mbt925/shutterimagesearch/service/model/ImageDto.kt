package com.mbt925.shutterimagesearch.service.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageDto(
    @SerialName("id")
    val id: String,
    @SerialName("description")
    val description: String,
    @SerialName("assets")
    val assets: ImageAssetsDto,
)
