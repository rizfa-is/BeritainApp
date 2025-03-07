package com.issog.beritainapp.ui.di

import com.issog.core.domain.usecase.BeritainInteractor
import com.issog.core.domain.usecase.BeritainUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single<BeritainUseCase> { BeritainInteractor(get()) }
}