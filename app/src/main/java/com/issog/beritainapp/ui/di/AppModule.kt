package com.issog.beritainapp.ui.di

import com.issog.beritainapp.ui.home.HomeViewModel
import com.issog.core.domain.usecase.BeritainInteractor
import com.issog.core.domain.usecase.BeritainUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    single<BeritainUseCase> { BeritainInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
}