package com.issog.beritainapp

import com.issog.beritainapp.ui.di.useCaseModule
import com.issog.core.utils.base.BaseApplication
import org.koin.core.KoinApplication
import org.koin.core.module.Module

class BeritainApplication: BaseApplication() {
    override fun addModule(koin: KoinApplication, modules: ArrayList<Module>) {
        super.addModule(koin, modules)
        modules.add(useCaseModule)
        koin.modules(modules)
    }
}