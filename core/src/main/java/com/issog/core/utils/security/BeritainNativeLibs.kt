package com.issog.core.utils.security

object BeritainNativeLibs {
    init {
        System.loadLibrary("beritain-native-libs")
    }

    external fun beritainApiKey(): String
    external fun beritainBaseUrl(): String
    external fun getNewsSourceUrl(): String
    external fun getTopHeadlineByCategoryUrl(): String
}