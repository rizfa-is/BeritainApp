package com.issog.beritainapp

import com.issog.beritainapp.di.useCaseModule
import com.issog.beritainapp.di.viewModelModule
import com.issog.core.utils.base.BaseApplication
import org.koin.core.KoinApplication
import org.koin.core.module.Module

class BeritainApplication : BaseApplication() {
    override fun addModule(
        koin: KoinApplication,
        modules: ArrayList<Module>,
    ) {
        super.addModule(koin, modules)
        modules.add(useCaseModule)
        modules.add(viewModelModule)
        koin.modules(modules)
    }
}
