package com.mbt925.shutterimagesearch

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.paging.PagingData
import com.mbt925.shutterimagesearch.design.theme.ShutterImageSearchTheme
import com.mbt925.shutterimagesearch.domain.model.Image
import com.mbt925.shutterimagesearch.ui.images_list.SearchScreen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun checkSearchBar() {
        fireUpSearchScreen(flowOf(PagingData.from(listOf())))

        composeTestRule.onNodeWithTag("search_bar").assertExists()
    }

    @Test
    fun checkImagesList() {
        val image1 = Image("id1", "url1", "desc1")
        val image2 = Image("id2", "url2", "desc2")
        val imagesFlow: Flow<PagingData<Image>> = flowOf(
            PagingData.from(listOf(image1, image2))
        )

        fireUpSearchScreen(imagesFlow)

        composeTestRule.onNodeWithText(image1.description).assertExists()
        composeTestRule.onNodeWithText(image2.description).assertExists()

    }

    private fun fireUpSearchScreen(
        imagesFlow: Flow<PagingData<Image>>,
    ) {
        composeTestRule.setContent {
            ShutterImageSearchTheme {
                SearchScreen(
                    query = "",
                    onQuery = { },
                    imagesFlow = imagesFlow,
                )
            }
        }
    }

}
