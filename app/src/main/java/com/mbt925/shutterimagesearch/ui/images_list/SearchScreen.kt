package com.mbt925.shutterimagesearch.ui.images_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.mbt925.shutterimagesearch.R
import com.mbt925.shutterimagesearch.design.theme.SizeM
import com.mbt925.shutterimagesearch.design.theme.SizeS
import com.mbt925.shutterimagesearch.design.theme.SizeXS
import com.mbt925.shutterimagesearch.design.theme.SizeXXXS
import com.mbt925.shutterimagesearch.domain.model.Image
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    imagesFlow: Flow<PagingData<Image>>,
    query: String,
    onQuery: (String) -> Unit,
) {
    val images = imagesFlow.collectAsLazyPagingItems()
    val listState = rememberLazyListState()
    val context = rememberCoroutineScope()

    Scaffold(
        topBar = {
            SearchBar(
                query = query,
                onQuery = onQuery,
            )
        }
    ) { contentPadding ->

        LazyColumn(
            modifier = Modifier
                .padding(top = contentPadding.calculateTopPadding())
                .padding(top = SizeM, start = SizeM, end = SizeM),
            state = listState,
        ) {

            if (images.loadState.refresh is LoadState.Loading) {
                item { Loading() }
                context.launch { listState.scrollToItem(0) }
            }

            items(images, key = { it.id }) { image ->
                image?.let { ShutterImage(image = image) }
            }

            images.apply {
                when {
                    loadState.append is LoadState.Loading -> {
                        item { LoadingItem() }
                    }
                    loadState.refresh is LoadState.Error -> {
                        val e = images.loadState.refresh as LoadState.Error
                        item {
                            ErrorItem(
                                message = e.error.localizedMessage!!,
                                onClickRetry = { retry() }
                            )
                        }
                    }
                    loadState.append is LoadState.Error -> {
                        val e = images.loadState.append as LoadState.Error
                        item {
                            ErrorItem(
                                message = e.error.localizedMessage!!,
                                onClickRetry = { retry() }
                            )
                        }
                    }
                }

                item {
                    Spacer(Modifier.padding(bottom = contentPadding.calculateBottomPadding()))
                }
            }

        }

    }
}

@Composable
private fun ShutterImage(
    image: Image,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = SizeXXXS),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Column(
            modifier = Modifier.padding(SizeS),
            verticalArrangement = Arrangement.spacedBy(SizeXS),
        ) {
            AsyncImage(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .align(CenterHorizontally),
                model = image.url,
                contentDescription = image.description,
            )
            Text(text = image.description,
                style = MaterialTheme.typography.bodySmall)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar(
    query: String,
    onQuery: (String) -> Unit,
) {
    Box(
        modifier = Modifier.testTag("search_bar")
            .background(MaterialTheme.colorScheme.primary)
            .statusBarsPadding(),
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = query,
            singleLine = true,
            shape = MaterialTheme.shapes.extraLarge,
            placeholder = { Text(stringResource(R.string.search_hint)) },
            label = { Text(stringResource(R.string.search)) },
            onValueChange = onQuery,
        )
    }
}

@Composable
fun Loading() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun LoadingItem() {
    LinearProgressIndicator(
        modifier = Modifier
            .padding(vertical = SizeS)
            .fillMaxWidth()
    )
}

@Composable
private fun ErrorItem(
    message: String,
    onClickRetry: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(vertical = SizeXXXS)
            .background(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.shapes.medium)
            .padding(SizeS)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message,
            maxLines = 1,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Red
        )
        OutlinedButton(onClick = onClickRetry) {
            Text(text = stringResource(R.string.try_again))
        }
    }
}
