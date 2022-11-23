package com.mbt925.shutterimagesearch.ui.images_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.mbt925.shutterimagesearch.domain.ImageRepository
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class MainViewModel(
    private val repository: ImageRepository,
) : ViewModel() {

    val queryText = MutableStateFlow("")
    val imagesList = queryText
        .debounce(2.seconds)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            repository.searchImages(query)
        }
        .cachedIn(viewModelScope)

}
