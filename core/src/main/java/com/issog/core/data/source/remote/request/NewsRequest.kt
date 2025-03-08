package com.issog.core.data.source.remote.request

data class NewsRequest(
    var category: String = "",
    var sourceId: String = "",
    var page: Int = 1,
    var pageSize: Int = 5,
    var search: String = ""
)
