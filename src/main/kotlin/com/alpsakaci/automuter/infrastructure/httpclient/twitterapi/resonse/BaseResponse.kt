package com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.resonse

data class BaseResponse<D, M>(
    val data: D?,
    val meta: M?
)
