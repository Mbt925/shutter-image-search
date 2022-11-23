package com.mbt925.shutterimagesearch.di

import com.mbt925.shutterimagesearch.ui.images_list.MainViewModel
import com.mbt925.shutterimagesearch.data.ImageRemoteDataSource
import com.mbt925.shutterimagesearch.data.ImageRepositoryImpl
import com.mbt925.shutterimagesearch.service.ImagesService
import com.mbt925.shutterimagesearch.domain.ImageRepository
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {

    viewModel {
        MainViewModel(get())
    }

    factory<ImageRepository> {
        ImageRepositoryImpl(
            dataSource = get(),
            dispatcher = Dispatchers.IO,
        )
    }

    factory {
        ImageRemoteDataSource(
            service = get(),
            Dispatchers.IO,
        )
    }

    single { get<Retrofit>().create(ImagesService::class.java) }

}
