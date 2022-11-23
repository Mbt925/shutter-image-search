package com.mbt925.shutterimagesearch.service

import com.mbt925.shutterimagesearch.service.model.ImageAssetDto
import com.mbt925.shutterimagesearch.service.model.ImageAssetsDto
import com.mbt925.shutterimagesearch.service.model.ImageDto
import com.mbt925.shutterimagesearch.service.model.ImagesPageDto
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

class SearchImagesTest {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    @Test
    fun searchImages() {

        val expected = ImagesPageDto(
            page = 2,
            totalCount = 1,
            images = listOf(
                ImageDto(
                    id = "1",
                    description = "desc1",
                    assets = ImageAssetsDto(
                        preview = ImageAssetDto("url11"),
                        small_thumb = ImageAssetDto("url12"),
                        large_thumb = ImageAssetDto("url13"),
                    ),
                ),
            )
        )

        val payload = """
            {"page":2,"per_page":10,"total_count":1,"search_id":"1123213","data":[{"id":"1","aspect":1.5,"assets":{"preview":{"height":300,"url":"url11","width":450},"small_thumb":{"height":0,"url":"url12","width":0},"large_thumb":{"height":0,"url":"url13","width":0},"huge_thumb":{"height":260,"url":"https://image.shutterstock.com/image-photo/portrait-happy-young-black-woman-260nw-2134545713.jpg","width":390},"preview_1000":{"url":"https://ak.picdn.net/shutterstock/photos/2134545713/watermark_1000/32f3efe79979ef7179319fe4afeb2c4b/preview_1000-2134545713.jpg","width":1000,"height":667},"preview_1500":{"url":"https://image.shutterstock.com/z/stock-photo-portrait-of-happy-young-black-woman-relaxing-on-wooden-deck-chair-at-tropical-beach-while-looking-2134545713.jpg","width":1500,"height":1000}},"contributor":{"id":"301539971"},"description":"desc1","image_type":"photo","has_model_release":true,"media_type":"image"}]}
            """

        assertEquals(
            expected,
            json.decodeFromString(ImagesPageDto.serializer(), payload),
        )

    }

}
