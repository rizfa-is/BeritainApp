package com.issog.core.utils.security

object BeritainNativeLibs {
    init {
        System.loadLibrary("beritain-native-libs")
    }

    external fun beritainApiKey(): String
    external fun beritainBaseUrl(): String
    external fun getNewsSourceUrl(): String
    external fun getTopHeadlineByCategoryUrl(): String
    external fun beritainDb(): String
    external fun beritainPassphrase(): String
    external fun beritainHostname(): String
    external fun beritainCertPinner1(): String
    external fun beritainCertPinner2(): String
    external fun beritainCertPinner3(): String
}