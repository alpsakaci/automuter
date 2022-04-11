package com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.resonse

data class MetaDataResponse(
    val result_count: Int,
    val next_token: String?,
    val previous_token: String?
)
