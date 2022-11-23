package com.mbt925.shutterimagesearch.data

import com.mbt925.shutterimagesearch.service.ImagesService
import com.mbt925.shutterimagesearch.service.model.ImagesPageDto
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class ImageRemoteDataSourceTest {

    @get:Rule
    val rule = com.mbt925.shutterimagesearch.SynchronousExecutionRule()

    private val service: ImagesService = mockk(relaxed = true)

    @Test
    fun onSearchImages_callsService() = rule.runBlockingTest {
        val call = mockk<Call<ImagesPageDto>> {
            every { execute() } returns Response.success(null)
        }
        every { service.searchImages(any(), any(), any()) } returns call

        val dataSource = getDataSource()

        val q = "query"
        val p = 1
        val ps = 10
        dataSource.searchImages(q, p, ps)

        verify { service.searchImages(q, p, ps) }
    }

    private fun getDataSource() = ImageRemoteDataSource(
        service = service,
        dispatcher = rule.dispatcher,
    )
}
