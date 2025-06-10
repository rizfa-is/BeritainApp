package com.issog.core.utils.base

import com.google.android.play.core.splitcompat.SplitCompatApplication
import com.issog.core.di.databaseModule
import com.issog.core.di.networkModule
import com.issog.core.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

abstract class BaseApplication: SplitCompatApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@BaseApplication)
            addModule(
                this,
                arrayListOf(databaseModule, networkModule, repositoryModule)
            )
        }
    }

    open fun addModule(koin: KoinApplication, modules: ArrayList<Module>) {}
}