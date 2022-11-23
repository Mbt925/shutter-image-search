package com.mbt925.shutterimagesearch.data.mapper

import com.mbt925.shutterimagesearch.domain.model.Image
import com.mbt925.shutterimagesearch.service.model.ImageDto

fun ImageDto.toImage(): Image = Image(
    id = id,
    description = description,
    url = assets.preview.url,
)
