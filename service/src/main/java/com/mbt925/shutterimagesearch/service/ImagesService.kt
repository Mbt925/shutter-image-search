package com.mbt925.shutterimagesearch.service

import com.mbt925.shutterimagesearch.service.model.ImagesPageDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesService {

    companion object {
        private const val KEY_PARAM_QUERY = "query"
        private const val KEY_PARAM_PAGE = "page"
        private const val KEY_PARAM_PAGE_SIZE = "per_page"
        const val PAGE_SIZE = 10
    }

    @GET("images/search")
    fun searchImages(
        @Query(KEY_PARAM_QUERY) query: String,
        @Query(KEY_PARAM_PAGE) page: Int,
        @Query(KEY_PARAM_PAGE_SIZE) pageSize: Int,
    ): Call<ImagesPageDto>

}
