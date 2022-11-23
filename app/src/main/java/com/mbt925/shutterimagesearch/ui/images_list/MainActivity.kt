package com.mbt925.shutterimagesearch.ui.images_list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.core.view.WindowCompat
import com.mbt925.shutterimagesearch.design.theme.ShutterImageSearchTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ShutterImageSearchTheme {
                SearchScreen(
                    query = viewModel.queryText.collectAsState().value,
                    onQuery = { viewModel.queryText.value = it },
                    imagesFlow = viewModel.imagesList,
                )
            }
        }
    }
}
